package com.unhasystem.unhas.repositories;

import com.unhasystem.unhas.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

     List<Servico> findByAtivo(boolean ativo);
}
