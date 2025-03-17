package com.unhasystem.unhas.services;

import com.unhasystem.unhas.entities.Agendamento;
import com.unhasystem.unhas.entities.Cliente;
import com.unhasystem.unhas.entities.Servico;
import com.unhasystem.unhas.repositories.AgendamentoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service

public class AgendamentoService {

     private static final Logger logger = LoggerFactory.getLogger(AgendamentoService.class);

     @Autowired
     private AgendamentoRepository agendamentoRepository;

     @Autowired
     private ServicoService servicoService;

     public List<Agendamento> findByCliente(Cliente cliente) {
          return agendamentoRepository.findByCliente(cliente);
     }

     public List<Agendamento> findAll() {
          return agendamentoRepository.findAll();
     }

//     public List<Agendamento> findByDate(LocalDate data) {
//          ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
//          LocalDateTime startOfDay = data.atStartOfDay(zoneId).toLocalDateTime();
//          LocalDateTime endOfDay = data.atTime(23, 59, 59).atZone(zoneId).toLocalDateTime();
//          List<Agendamento> agendamentos = agendamentoRepository.findByDataHoraInicioBetween(startOfDay, endOfDay);
//          logger.info("Agendamentos encontrados para {}: {}", data, agendamentos);
//          return agendamentos != null ? agendamentos : Collections.emptyList();
//     }

     public List<Agendamento> findByDate(LocalDate data) {
          // Fuso horário da aplicação (ou da região dos usuários)
          ZoneId zoneId = ZoneId.of("America/Sao_Paulo");

          // Criando início e fim do dia no fuso horário de SP
          ZonedDateTime startOfDay = data.atStartOfDay(zoneId); // 2025-03-18T00:00-03:00
          ZonedDateTime endOfDay = data.atTime(23, 59, 59).atZone(zoneId); // 2025-03-18T23:59:59-03:00

          // Convertendo o intervalo para UTC, já que o banco trabalha em UTC
          ZonedDateTime startUtc = startOfDay.withZoneSameInstant(ZoneOffset.UTC); // converte mantendo o instante
          ZonedDateTime endUtc = endOfDay.withZoneSameInstant(ZoneOffset.UTC);

          // Convertendo para LocalDateTime porque o JPA trabalha com LocalDateTime puro (sem fuso)
          LocalDateTime start = startUtc.toLocalDateTime();
          LocalDateTime end = endUtc.toLocalDateTime();

          // Fazendo a busca no banco de dados com o intervalo correto
          List<Agendamento> agendamentos = agendamentoRepository.findByDataHoraInicioBetween(start, end);

          logger.info("Qtd agendamentos encontrados para {}: {}", data, agendamentos.size());
          return agendamentos != null ? agendamentos : Collections.emptyList();
     }



     @Transactional
     public Agendamento criar(Cliente cliente, Long servicoId, LocalDateTime dataHoraInicio) {
          // Buscar o serviço
          Optional<Servico> servicoOpt = servicoService.findById(servicoId);
          if (!servicoOpt.isPresent()) {
               throw new RuntimeException("Serviço não encontrado");
          }
          Servico servico = servicoOpt.get();

          // Calcular o horário de término baseado na duração do serviço
          LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(servico.getDuracaoMinutos());

          // Verificar se existe conflito de horário
          if (agendamentoRepository.existsAgendamentoConflitante(dataHoraInicio, dataHoraFim)) {
               throw new RuntimeException("Horário já está ocupado");
          }

          // Criar o agendamento
          Agendamento agendamento = new Agendamento();
          agendamento.setCliente(cliente);
          agendamento.setServico(servico);
          agendamento.setDataHoraInicio(dataHoraInicio);
          agendamento.setDataHoraFim(dataHoraFim);
          agendamento.setValorTotal(servico.getPreco());
          agendamento.setStatus(Agendamento.StatusAgendamento.AGENDADO);

          return agendamentoRepository.save(agendamento);
     }

     @Transactional
     public Agendamento atualizarStatus(Long agendamentoId, Agendamento.StatusAgendamento novoStatus) {
          Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(agendamentoId);
          if (!agendamentoOpt.isPresent()) {
               throw new RuntimeException("Agendamento não encontrado");
          }

          Agendamento agendamento = agendamentoOpt.get();
          agendamento.setStatus(novoStatus);

          return agendamentoRepository.save(agendamento);
     }

     public List<Agendamento> findAgendamentosAtivos(LocalDateTime inicio, LocalDateTime fim) {
          return agendamentoRepository.findAgendamentosAtivos(inicio, fim);
     }

     public boolean verificarDisponibilidade(LocalDateTime inicio, LocalDateTime fim) {
          return !agendamentoRepository.existsAgendamentoConflitante(inicio, fim);
     }
}
