package com.unhasystem.unhas.controllers;

import com.unhasystem.unhas.entities.Agendamento;
import com.unhasystem.unhas.entities.Cliente;
import com.unhasystem.unhas.entities.ItemPortfolio;
import com.unhasystem.unhas.entities.Servico;
import com.unhasystem.unhas.services.AgendamentoService;
import com.unhasystem.unhas.services.ClienteService;
import com.unhasystem.unhas.services.PortfolioService;
import com.unhasystem.unhas.services.ServicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

     @Autowired
     private AgendamentoService agendamentoService;

     @Autowired
     private ClienteService clienteService;

     @Autowired
     private ServicoService servicoService;

     @Autowired
     private PortfolioService portfolioService;

     private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

     @GetMapping("/dashboard")
     public String dashboard(Model model) {
          LocalDate today = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
          List<Agendamento> agendamentos = agendamentoService.findByDate(today);
          if (agendamentos == null) {
               agendamentos = Collections.emptyList();
               logger.warn("Agendamentos retornados como null, usando lista vazia.");
          }
          logger.info("Agendamentos para o dashboard ({}): {}", today, agendamentos);
          model.addAttribute("agendamentosHoje", agendamentos);
          model.addAttribute("data", today);
          return "admin/dashboard";
     }

     @GetMapping("/agendamentos")
     public String listarAgendamentos(
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
             Model model) {
          LocalDate dataConsulta = (data != null) ? data : LocalDate.now(ZoneId.of("America/Sao_Paulo"));
          List<Agendamento> agendamentos = agendamentoService.findByDate(dataConsulta);


          // Verificar se os agendamentos estão sendo retornados corretamente
          logger.info("Agendamentos para listarAgendamentos ({}): {}", dataConsulta, agendamentos);
          logger.info("Número de agendamentos encontrados: {}", agendamentos.size());

          model.addAttribute("agendamentos", agendamentos);
          model.addAttribute("dataConsulta", dataConsulta);
          return "admin/agendamentos";
     }

     @GetMapping("/clientes")
     public String listarClientes(Model model) {
          List<Cliente> clientes = clienteService.findAll();
          model.addAttribute("clientes", clientes);
          return "admin/clientes";
     }

     @GetMapping("/servicos")
     public String listarServicos(Model model) {
          List<Servico> servicos = servicoService.findAll();
          model.addAttribute("servicos", servicos);
          model.addAttribute("novoServico", new Servico());
          return "admin/servicos";
     }

     @PostMapping("/servicos/criar")
     public String criarServico(@ModelAttribute Servico servico, RedirectAttributes redirectAttributes) {
          servicoService.save(servico);
          redirectAttributes.addFlashAttribute("mensagem", "Serviço criado com sucesso!");
          return "redirect:/admin/servicos";
     }

     @PostMapping("/servicos/{id}/atualizar")
     public String atualizarServico(@PathVariable Long id, @ModelAttribute Servico servico,
                                    RedirectAttributes redirectAttributes) {
          servico.setId(id);
          servicoService.save(servico);
          redirectAttributes.addFlashAttribute("mensagem", "Serviço atualizado com sucesso!");
          return "redirect:/admin/servicos";
     }

     @PostMapping("/servicos/{id}/desativar")
     public String desativarServico(@PathVariable Long id, RedirectAttributes redirectAttributes) {
          servicoService.desativar(id);
          redirectAttributes.addFlashAttribute("mensagem", "Serviço desativado com sucesso!");
          return "redirect:/admin/servicos";
     }

     @GetMapping("/portfolio")
     public String listarPortfolio(Model model) {
          List<ItemPortfolio> portfolioItems = portfolioService.findAll();
          List<Servico> servicos = servicoService.findAtivos();

          model.addAttribute("portfolioItems", portfolioItems);
          model.addAttribute("servicos", servicos);
          model.addAttribute("novoItem", new ItemPortfolio());

          return "admin/portfolio";
     }

     @PostMapping("/portfolio/criar")
     public String criarItemPortfolio(@ModelAttribute ItemPortfolio item,
                                      @RequestParam("servicoId") Long servicoId,
                                      @RequestParam("imagem") MultipartFile imagem,
                                      RedirectAttributes redirectAttributes) {
          try {
               // Associar serviço ao item de portfólio
               if (servicoId != null) {
                    servicoService.findById(servicoId).ifPresent(item::setServico);
               }

               portfolioService.save(item, imagem);
               redirectAttributes.addFlashAttribute("mensagem", "Item de portfólio criado com sucesso!");
          } catch (IOException e) {
               redirectAttributes.addFlashAttribute("erro", "Erro ao salvar imagem: " + e.getMessage());
          }

          return "redirect:/admin/portfolio";
     }

     @PostMapping("/portfolio/{id}/excluir")
     public String excluirItemPortfolio(@PathVariable Long id, RedirectAttributes redirectAttributes) {
          portfolioService.delete(id);
          redirectAttributes.addFlashAttribute("mensagem", "Item de portfólio excluído com sucesso!");
          return "redirect:/admin/portfolio";
     }

     @PostMapping("/agendamentos/{id}/atualizar-status")
     public String atualizarStatusAgendamento(
             @PathVariable Long id,
             @RequestParam Agendamento.StatusAgendamento status,
             RedirectAttributes redirectAttributes) {

          try {
               agendamentoService.atualizarStatus(id, status);
               redirectAttributes.addFlashAttribute("mensagem", "Status do agendamento atualizado com sucesso!");
          } catch (Exception e) {
               redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar status: " + e.getMessage());
          }

          return "redirect:/admin/agendamentos";
     }
}
