package com.unhasystem.unhas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Continua permitindo o uso de @PreAuthorize e @PostAuthorize
public class SecurityConfig {

     @Autowired
     private UserDetailsService userDetailsService;

     // Bean para codificar as senhas (BCrypt)
     @Bean
     public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }

     // Bean do AuthenticationManager (agora precisa ser declarado)
     @Bean
     public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
          AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
          authManagerBuilder
                  .userDetailsService(userDetailsService)
                  .passwordEncoder(passwordEncoder());
          return authManagerBuilder.build();
     }

     // Configuração de segurança HTTP usando SecurityFilterChain
     @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http
                  .authorizeHttpRequests(auth -> auth
//                          .requestMatchers("/", "/portfolio/**", "/uploads/**", "/css/**", "/js/**", "/img/**", "/registro", "/login").permitAll()
//                          .requestMatchers("/admin/**").hasRole("ADMIN")
//                          .requestMatchers("/cliente/**", "/agendamento/**").hasRole("CLIENT")
//                          .anyRequest().authenticated()
                                  .anyRequest().permitAll()
                  )
                  .formLogin(form -> form
                          .loginPage("/login")
                          .defaultSuccessUrl("/", true)
                          .permitAll()
                  )
                  .logout(logout -> logout
                          .logoutSuccessUrl("/")
                          .permitAll()
                  );

          return http.build();
     }
}