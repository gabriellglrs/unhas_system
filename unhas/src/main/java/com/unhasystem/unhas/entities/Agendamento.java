package com.unhasystem.unhas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agendamento")
public class Agendamento {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @ManyToOne
     @JoinColumn(name = "cliente_id", nullable = false)
     private Cliente cliente;

     @ManyToOne
     @JoinColumn(name = "servico_id", nullable = false)
     private Servico servico;

     @Column(nullable = false)
     private LocalDateTime dataHoraInicio;

     @Column(nullable = false)
     private LocalDateTime dataHoraFim;

     @Column(nullable = false)
     private BigDecimal valorTotal;

     @Enumerated(EnumType.STRING)
     private StatusAgendamento status = StatusAgendamento.AGENDADO;

     @Column(name = "data_criacao")
     private LocalDateTime dataCriacao;

     @PrePersist
     public void prePersist() {
          dataCriacao = LocalDateTime.now();
     }

     public enum StatusAgendamento {
          AGENDADO, CONFIRMADO, CONCLUIDO, CANCELADO
     }
}
