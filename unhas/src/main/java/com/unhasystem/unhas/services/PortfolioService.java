package com.unhasystem.unhas.services;

import com.unhasystem.unhas.entities.ItemPortfolio;
import com.unhasystem.unhas.entities.Servico;
import com.unhasystem.unhas.repositories.ItemPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PortfolioService {

     @Autowired
     private ItemPortfolioRepository portfolioRepository;

     @Autowired
     private ServicoService servicoService;

     private final Path uploadPath = Paths.get("./uploads/portfolio");

     public PortfolioService() {
          try {
               Files.createDirectories(uploadPath);
          } catch (IOException e) {
               throw new RuntimeException("Não foi possível criar o diretório para upload de imagens", e);
          }
     }

     public List<ItemPortfolio> findAll() {
          return portfolioRepository.findAll();
     }

     public List<ItemPortfolio> findRecentes() {
          return portfolioRepository.findTop10ByOrderByDataPublicacaoDesc();
     }

     public Optional<ItemPortfolio> findById(Long id) {
          return portfolioRepository.findById(id);
     }

     public List<ItemPortfolio> findByServico(Long servicoId) {
          Optional<Servico> servicoOpt = servicoService.findById(servicoId);
          if (servicoOpt.isPresent()) {
               return portfolioRepository.findByServico(servicoOpt.get());
          }
          return List.of();
     }

     public ItemPortfolio save(ItemPortfolio item, MultipartFile imagem) throws IOException {
          if (imagem != null && !imagem.isEmpty()) {
               String filename = UUID.randomUUID().toString() + "_" + imagem.getOriginalFilename();
               Path filePath = uploadPath.resolve(filename);
               Files.copy(imagem.getInputStream(), filePath);
               item.setImagemUrl("/uploads/portfolio/" + filename);
          }

          return portfolioRepository.save(item);
     }

     public void delete(Long id) {
          portfolioRepository.deleteById(id);
     }
}