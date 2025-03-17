package com.unhasystem.unhas.repositories;

import com.unhasystem.unhas.entities.Agendamento;
import com.unhasystem.unhas.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

     List<Agendamento> findByCliente(Cliente cliente);

     List<Agendamento> findByDataHoraInicioBetween(LocalDateTime inicio, LocalDateTime fim);

     @Query("SELECT a FROM Agendamento a WHERE a.dataHoraInicio BETWEEN :inicio AND :fim")
     List<Agendamento> buscarPorData(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);


     @Query("SELECT a FROM Agendamento a WHERE a.dataHoraInicio >= :inicio AND a.dataHoraInicio < :fim AND a.status != 'CANCELADO'")
     List<Agendamento> findAgendamentosAtivos(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

     @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Agendamento a " +
            "WHERE ((a.dataHoraInicio <= :fim AND a.dataHoraFim >= :inicio) OR " +
            "(a.dataHoraInicio >= :inicio AND a.dataHoraInicio < :fim)) " +
            "AND a.status != 'CANCELADO'")
     boolean existsAgendamentoConflitante(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
