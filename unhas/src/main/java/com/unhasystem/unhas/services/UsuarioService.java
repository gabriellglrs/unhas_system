package com.unhasystem.unhas.services;

import com.unhasystem.unhas.entities.Usuario;
import com.unhasystem.unhas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

     @Autowired
     private UsuarioRepository usuarioRepository;

     public Optional<Usuario> findByUsername(String username) {
          return usuarioRepository.findByUsername(username);
     }

     public Usuario save(Usuario usuario) {
          return usuarioRepository.save(usuario);
     }

     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          Usuario usuario = usuarioRepository.findByUsername(username)
                  .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

          return new User(
                  usuario.getUsername(),
                  usuario.getPassword(),
                  usuario.isAtivo(),
                  true,
                  true,
                  true,
                  Collections.singleton(new SimpleGrantedAuthority(usuario.getRole()))
          );
     }
}
