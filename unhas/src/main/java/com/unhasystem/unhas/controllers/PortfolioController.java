package com.unhasystem.unhas.controllers;

import com.unhasystem.unhas.entities.ItemPortfolio;
import com.unhasystem.unhas.entities.Servico;
import com.unhasystem.unhas.services.PortfolioService;
import com.unhasystem.unhas.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

     @Autowired
     private PortfolioService portfolioService;

     @Autowired
     private ServicoService servicoService;

     @GetMapping
     public String listarTodos(Model model) {
          List<ItemPortfolio> items = portfolioService.findAll();
          List<Servico> servicos = servicoService.findAtivos();

          model.addAttribute("portfolioItems", items);
          model.addAttribute("servicos", servicos);

          return "portfolio";
     }

     @GetMapping("/servico/{id}")
     public String listarPorServico(@PathVariable Long id, Model model) {
          List<ItemPortfolio> items = portfolioService.findByServico(id);
          Optional<Servico> servicoOpt = servicoService.findById(id);
          List<Servico> servicos = servicoService.findAtivos();

          model.addAttribute("portfolioItems", items);
          model.addAttribute("servicoSelecionado", servicoOpt.orElse(null));
          model.addAttribute("servicos", servicos);

          return "portfolio";
     }

     @GetMapping("/item/{id}")
     public String detalharItem(@PathVariable Long id, Model model) {
          Optional<ItemPortfolio> itemOpt = portfolioService.findById(id);

          if (itemOpt.isPresent()) {
               model.addAttribute("item", itemOpt.get());
               return "portfolio-item";
          } else {
               return "redirect:/portfolio";
          }
     }
}
