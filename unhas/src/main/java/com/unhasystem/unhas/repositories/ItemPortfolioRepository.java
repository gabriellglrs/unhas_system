package com.unhasystem.unhas.repositories;

import com.unhasystem.unhas.entities.ItemPortfolio;
import com.unhasystem.unhas.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPortfolioRepository extends JpaRepository<ItemPortfolio, Long> {

     List<ItemPortfolio> findByServico(Servico servico);

     List<ItemPortfolio> findTop10ByOrderByDataPublicacaoDesc();
}
