package com.unhasystem.unhas;

import com.unhasystem.unhas.entities.Usuario;
import com.unhasystem.unhas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UnhasApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnhasApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(UsuarioRepository usuarioRepository) {
		return args -> {
			// Verificar se já existe um usuário admin
			if (!usuarioRepository.existsByUsername("admin@admin.com")) {
				// Criar usuário admin
				Usuario admin = new Usuario();
				admin.setUsername("admin@admin.com");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRole("ROLE_ADMIN");
				admin.setAtivo(true);
				usuarioRepository.save(admin);

				System.out.println("Usuário administrador criado com sucesso!");
				System.out.println("Username: admin@admin.com");
				System.out.println("Senha: admin123");
			}
		};
	}

}
