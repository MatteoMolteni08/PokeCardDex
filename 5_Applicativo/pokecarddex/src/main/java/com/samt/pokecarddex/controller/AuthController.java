package com.samt.pokecarddex.controller;

import com.samt.pokecarddex.dto.RegistrationRequest;
import com.samt.pokecarddex.model.User;
import com.samt.pokecarddex.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@CrossOrigin(origins = "*") // Permette le chiamate da qualsiasi frontend in fase di sviluppo
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody RegistrationRequest request) {
        try {
            // Invoca il servizio di registrazione
            User registeredUser = userService.registerNewUser(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Utente registrato con successo! ID: " + registeredUser.getId());
        } catch (RuntimeException e) {
            // Cattura gli errori generati dal Service (es. email/username duplicati)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
