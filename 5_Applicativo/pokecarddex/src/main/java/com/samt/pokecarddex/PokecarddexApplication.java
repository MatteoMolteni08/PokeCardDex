package com.samt.pokecarddex;

import com.samt.pokecarddex.model.User;
import com.samt.pokecarddex.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.boot.CommandLineRunner;
import com.samt.pokecarddex.model.User;
import com.samt.pokecarddex.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PokecarddexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokecarddexApplication.class, args);
	}


	@Bean
	public CommandLineRunner dataLoader(UserRepository userRepo, PasswordEncoder encoder) {
		return args -> {
			if (userRepo.findByUsername("admin").isEmpty()) {
				User admin = new User();
				admin.setUsername("admin");
				// Cripta la password "password123" prima di salvarla
				admin.setPassword(encoder.encode("password123"));
				admin.setRole("ROLE_USER");
				userRepo.save(admin);
				System.out.println("Utente di test 'admin' creato nel DB con successo!");
			}
		};
	}
}


