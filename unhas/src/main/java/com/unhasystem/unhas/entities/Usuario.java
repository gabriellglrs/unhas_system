package com.unhasystem.unhas.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false, unique = true)
     private String username;

     @Column(nullable = false)
     private String password;

     @Column(nullable = false)
     private String role; // ROLE_ADMIN, ROLE_CLIENT

     private boolean ativo = true;
}