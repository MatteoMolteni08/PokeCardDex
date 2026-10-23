package com.samt.pokecarddex.service;

import com.samt.pokecarddex.dto.RegistrationRequest;
import com.samt.pokecarddex.model.User;
import com.samt.pokecarddex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Istanza del codificatore di password (richiede Spring Security nel pom.xml)
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerNewUser(RegistrationRequest request) {

        // 1. Controllo se l'email è già utilizzata
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Errore: Questa email è già registrata.");
        }

        // 2. Controllo se lo username (nome) è già utilizzato
        if (userRepository.existsByNome(request.getUsername())) {
            throw new RuntimeException("Errore: Questo username è già in uso.");
        }

        // 3. Creazione e mappatura dell'entità User
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setNome(request.getUsername());

        // Cripta la password prima di salvarla
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(hashedPassword);

        // 4. Salvataggio definitivo nel database
        return userRepository.save(newUser);


    }
    @Override
    public UserDetails loadUserByUsername(String loginInput) throws UsernameNotFoundException {
        // Deve ritornare DIRETTAMENTE il tuo utente del database
        return userRepository.findByNomeOrEmail(loginInput, loginInput)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
    }

}

