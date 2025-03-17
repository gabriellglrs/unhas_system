package com.unhasystem.unhas.repositories;

import com.unhasystem.unhas.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

     List<Horario> findByDiaSemanaAndDisponivel(DayOfWeek diaSemana, boolean disponivel);
}
