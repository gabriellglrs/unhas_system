package com.unhasystem.unhas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "portfolio_item")
public class ItemPortfolio {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false)
     private String titulo;

     private String descricao;

     @Column(nullable = false)
     private String imagemUrl;

     @ManyToOne
     @JoinColumn(name = "servico_id")
     private Servico servico;

     @Column(name = "data_publicacao")
     private LocalDateTime dataPublicacao;

     @PrePersist
     public void prePersist() {
          dataPublicacao = LocalDateTime.now();
     }
}
