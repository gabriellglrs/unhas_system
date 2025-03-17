package com.unhasystem.unhas.services;

import com.unhasystem.unhas.entities.Cliente;
import com.unhasystem.unhas.entities.Usuario;
import com.unhasystem.unhas.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

     @Autowired
     private ClienteRepository clienteRepository;

     @Autowired
     private UsuarioService usuarioService;

     @Autowired
     private PasswordEncoder passwordEncoder;

     public List<Cliente> findAll() {
          return clienteRepository.findAll();
     }

     public Optional<Cliente> findById(Long id) {
          return clienteRepository.findById(id);
     }

     public Optional<Cliente> findByEmail(String email) {
          return clienteRepository.findByEmail(email);
     }

     @Transactional
     public Cliente cadastrar(Cliente cliente, String senha) {
          // Verificar se já existe um cliente com este email
          if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
               throw new RuntimeException("Email já cadastrado");
          }

          // Criar usuário
          Usuario usuario = new Usuario();
          usuario.setUsername(cliente.getEmail());
          usuario.setPassword(passwordEncoder.encode(senha));
          usuario.setRole("ROLE_CLIENT");
          usuario.setAtivo(true);

          usuario = usuarioService.save(usuario);

          // Associar usuário ao cliente
          cliente.setUsuario(usuario);

          return clienteRepository.save(cliente);
     }

     public Cliente save(Cliente cliente) {
          return clienteRepository.save(cliente);
     }
}
