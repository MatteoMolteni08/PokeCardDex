package com.samt.pokecarddex.service;

import com.samt.pokecarddex.model.User;
import com.samt.pokecarddex.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginInput) throws UsernameNotFoundException {
        // Cerchiamo nel DB passando l'input sia al campo nome che al campo email
        User user = userRepository.findByNomeOrEmail(loginInput, loginInput)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con Nome o Email: " + loginInput));

        // Restituiamo l'utente a Spring Security.
        // Usiamo l'email come identificativo principale di sessione.
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
