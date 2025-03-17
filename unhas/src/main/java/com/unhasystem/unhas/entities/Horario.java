package com.unhasystem.unhas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horario")
public class Horario {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false)
     private LocalTime horaInicio;

     @Column(nullable = false)
     private LocalTime horaFim;

     @Enumerated(EnumType.STRING)
     private DayOfWeek diaSemana;

     private boolean disponivel = true;
}