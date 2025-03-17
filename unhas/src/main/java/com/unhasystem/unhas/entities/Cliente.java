package com.unhasystem.unhas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false)
     private String nome;

     @Column(nullable = false, unique = true)
     private String email;

     private String telefone;

     @Column(name = "data_cadastro")
     private LocalDateTime dataCadastro;

     @OneToOne
     @JoinColumn(name = "usuario_id")
     private Usuario usuario;

     @OneToMany(mappedBy = "cliente")
     private List<Agendamento> agendamentos;

     @PrePersist
     public void prePersist() {
          dataCadastro = LocalDateTime.now();
     }
}