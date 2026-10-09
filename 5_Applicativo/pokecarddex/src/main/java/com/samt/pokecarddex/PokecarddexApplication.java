package com.samt.pokecarddex;

import com.samt.pokecarddex.model.User;
import com.samt.pokecarddex.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PokecarddexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokecarddexApplication.class, args);
	}
}


