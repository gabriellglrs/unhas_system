package com.unhasystem.unhas.services;

import com.unhasystem.unhas.entities.Servico;
import com.unhasystem.unhas.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

     @Autowired
     private ServicoRepository servicoRepository;

     public List<Servico> findAll() {
          return servicoRepository.findAll();
     }

     public List<Servico> findAtivos() {
          return servicoRepository.findByAtivo(true);
     }

     public Optional<Servico> findById(Long id) {
          return servicoRepository.findById(id);
     }

     public Servico save(Servico servico) {
          return servicoRepository.save(servico);
     }

     public void delete(Long id) {
          servicoRepository.deleteById(id);
     }

     public Servico desativar(Long id) {
          Optional<Servico> servicoOpt = servicoRepository.findById(id);
          if (servicoOpt.isPresent()) {
               Servico servico = servicoOpt.get();
               servico.setAtivo(false);
               return servicoRepository.save(servico);
          }
          throw new RuntimeException("Serviço não encontrado");
     }
}