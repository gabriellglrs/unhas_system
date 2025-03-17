package com.unhasystem.unhas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servico")
public class Servico {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false)
     private String nome;

     private String descricao;

     @Column(nullable = false)
     private BigDecimal preco;

     @Column(name = "duracao_minutos", nullable = false)
     private Integer duracaoMinutos;

     private boolean ativo = true;
}

