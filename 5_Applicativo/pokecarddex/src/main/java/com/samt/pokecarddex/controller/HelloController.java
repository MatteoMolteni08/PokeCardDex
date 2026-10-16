package com.samt.pokecarddex.controller;

import com.samt.pokecarddex.model.User;
import com.samt.pokecarddex.model.UserCard;
import com.samt.pokecarddex.repository.UserCardRepository;
import com.samt.pokecarddex.service.PokemonCardService;
import net.tcgdex.sdk.models.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    // Unico punto di contatto del controller è il Service di competenza
    private final PokemonCardService pokemonCardService;

    public HelloController(PokemonCardService pokemonCardService) {
        this.pokemonCardService = pokemonCardService;
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal User user) {
        // 1. Controlla di sicurezza se l'utente non è loggato (ridireziona al login)
        if (user == null) {
            return "redirect:/login";
        }
        Long userId = user.getId();

        List<Card> pokemonCards = pokemonCardService.getLastFiveCardsForUser(userId);

        model.addAttribute("carte", pokemonCards);
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Corrisponde a login.html in templates/
    }
}
