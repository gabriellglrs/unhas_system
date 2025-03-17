package com.unhasystem.unhas.controllers;

import com.unhasystem.unhas.entities.Usuario;
import com.unhasystem.unhas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

     @Autowired
     private UsuarioService usuarioService;

     @Autowired
     private PasswordEncoder passwordEncoder;

     @GetMapping("/login")
     public String loginPage() {
          return "login";
     }

     @GetMapping("/registro")
     public String registroPage(Model model) {
          model.addAttribute("usuario", new Usuario());
          return "registro";
     }

     @PostMapping("/registro")
     public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {

          // Verificar se o usuário já existe
          if (usuarioService.findByUsername(usuario.getUsername()).isPresent()) {
               result.rejectValue("username", "error.usuario", "Este nome de usuário já está em uso");
               return "registro";
          }

          if (result.hasErrors()) {
               return "registro";
          }

          // Codificar a senha e definir a role padrão como CLIENT
          usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
          usuario.setRole("ROLE_CLIENT");
          usuario.setAtivo(true);

          usuarioService.save(usuario);

          redirectAttributes.addFlashAttribute("mensagem", "Registro realizado com sucesso! Faça login para continuar.");
          return "redirect:/login";
     }
}

