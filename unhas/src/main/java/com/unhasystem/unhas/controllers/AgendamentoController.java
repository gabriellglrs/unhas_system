package com.unhasystem.unhas.controllers;

import com.unhasystem.unhas.entities.Agendamento;
import com.unhasystem.unhas.entities.Cliente;
import com.unhasystem.unhas.entities.Servico;
import com.unhasystem.unhas.services.AgendamentoService;
import com.unhasystem.unhas.services.ClienteService;
import com.unhasystem.unhas.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/agendamento")
public class AgendamentoController {

     @Autowired
     private AgendamentoService agendamentoService;

     @Autowired
     private ServicoService servicoService;

     @Autowired
     private ClienteService clienteService;

     @GetMapping("/novo")
     public String novoAgendamento(Model model) {
          List<Servico> servicos = servicoService.findAtivos();
          model.addAttribute("servicos", servicos);
          return "cliente/agendamento";
     }

     @GetMapping("/horarios-disponiveis")
     @ResponseBody
     public List<LocalTime> getHorariosDisponiveis(
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
             @RequestParam Long servicoId) {

          Optional<Servico> servicoOpt = servicoService.findById(servicoId);
          if (!servicoOpt.isPresent()) {
               return List.of();
          }

          Servico servico = servicoOpt.get();
          int duracaoServico = servico.getDuracaoMinutos();

          // Horários padrão de trabalho (exemplo)
          LocalTime horaInicio = LocalTime.of(9, 0);
          LocalTime horaFim = LocalTime.of(18, 0);

          // Lista para armazenar horários disponíveis
          List<LocalTime> horariosDisponiveis = new java.util.ArrayList<>();

          // Verificar agendamentos existentes nesta data
          List<Agendamento> agendamentosExistentes = agendamentoService.findByDate(data);

          // Gerar horários em intervalos de 30 minutos
          LocalTime horarioAtual = horaInicio;
          while (horarioAtual.plusMinutes(duracaoServico).isBefore(horaFim) ||
                 horarioAtual.plusMinutes(duracaoServico).equals(horaFim)) {

               LocalDateTime dataHoraInicio = LocalDateTime.of(data, horarioAtual);
               LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(duracaoServico);

               // Verificar se o horário está disponível
               if (agendamentoService.verificarDisponibilidade(dataHoraInicio, dataHoraFim)) {
                    horariosDisponiveis.add(horarioAtual);
               }

               // Avançar para o próximo horário (intervalos de 30 min)
               horarioAtual = horarioAtual.plusMinutes(30);
          }

          return horariosDisponiveis;
     }

     @PostMapping("/agendar")
     public String agendar(
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horario,
             @RequestParam Long servicoId,
             RedirectAttributes redirectAttributes) {

          try {
               Cliente cliente = getClienteLogado();
               LocalDateTime dataHoraAgendamento = LocalDateTime.of(data, horario);

               Agendamento agendamento = agendamentoService.criar(cliente, servicoId, dataHoraAgendamento);

               redirectAttributes.addFlashAttribute("mensagem", "Agendamento realizado com sucesso!");
               return "redirect:/cliente/historico";
          } catch (Exception e) {
               redirectAttributes.addFlashAttribute("erro", "Erro ao agendar: " + e.getMessage());
               return "redirect:/agendamento/novo";
          }
     }

     @PostMapping("/{id}/cancelar")
     public String cancelarAgendamento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
          try {
               agendamentoService.atualizarStatus(id, Agendamento.StatusAgendamento.CANCELADO);
               redirectAttributes.addFlashAttribute("mensagem", "Agendamento cancelado com sucesso!");
          } catch (Exception e) {
               redirectAttributes.addFlashAttribute("erro", "Erro ao cancelar agendamento: " + e.getMessage());
          }
          return "redirect:/cliente/historico";
     }

     private Cliente getClienteLogado() {
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          String username = auth.getName();

          Optional<Cliente> clienteOpt = clienteService.findByEmail(username);
          return clienteOpt.orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
     }
}

