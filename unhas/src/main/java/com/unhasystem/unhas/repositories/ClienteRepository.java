package com.unhasystem.unhas.repositories;

import com.unhasystem.unhas.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

     Optional<Cliente> findByEmail(String email);

     Optional<Cliente> findByUsuarioId(Long usuarioId);
}
