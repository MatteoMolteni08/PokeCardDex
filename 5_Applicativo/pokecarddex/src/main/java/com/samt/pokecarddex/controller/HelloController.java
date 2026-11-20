package com.samt.pokecarddex.controller;

import com.samt.pokecarddex.model.User;
import com.samt.pokecarddex.model.UserCard;
import com.samt.pokecarddex.repository.CardRepository;
import com.samt.pokecarddex.repository.UserCardRepository;
import com.samt.pokecarddex.service.PokemonCardService;
import net.tcgdex.sdk.TCGdex;
import net.tcgdex.sdk.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.tcgdex.sdk.TCGdex;
import net.tcgdex.sdk.models.SetResume;
import net.tcgdex.sdk.models.CardResume;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    // Unico punto di contatto del controller è il Service di competenza
    private final PokemonCardService pokemonCardService;
    private final TCGdex tcgdex;
    private CardRepository cardRepository;
    private ArrayList<SetResume> setList;

    public HelloController(PokemonCardService pokemonCardService) {
        this.pokemonCardService = pokemonCardService;
        this.tcgdex = new TCGdex("it");
        this.cardRepository = new CardRepository();

        // Li scarica solo la prima volta che un utente apre la pagina
        if (this.setList == null) {
            this.setList = new ArrayList<>();
            try {
                SetResume[] fetchedSets = tcgdex.fetchSets();
                if (fetchedSets != null) {
                    for (SetResume s : fetchedSets) {
                        if (s != null && s.getId() != null && !s.getFullSet().getSerie().getId().equalsIgnoreCase("tcgp")) {
                            this.setList.add(s);
                        }
                    }
                }
            } catch (Exception e) {
                // Evita il crash se TCGdex non risponde al primo tentativo
                this.setList = null;
            }
        }
    }

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        // 1. Controllo di sicurezza: se l'autenticazione manca o non è valida, vai al login
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return "redirect:/login";
        }
        // 2. Estraiamo il principal ed eseguiamo il cast sicuro alla tua classe User
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        List<Map<String, String>> pokemonCards = pokemonCardService.getLastFiveCardsForUser(userId);

        model.addAttribute("carte", pokemonCards);
        model.addAttribute("title", "Home Page");
        model.addAttribute("username", user.getUsername());
        return "index";
    }

    @GetMapping("/cardlist")
    public String cardlist(Model model, Authentication authentication) {
        // 1. Controllo di sicurezza: se l'autenticazione manca o non è valida, vai al login
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return "redirect:/login";
        }
        // 2. Estraiamo il principal ed eseguiamo il cast sicuro alla tua classe User
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        List<Map<String, String>> pokemonCards = pokemonCardService.getCardsForUser(userId);

        model.addAttribute("carte", pokemonCards);
        model.addAttribute("title", "Home Page");
        model.addAttribute("username", user.getUsername());
        return "yourCards";
    }


    @GetMapping("/addCard")
    public String add(Model model, Authentication authentication,
                      @RequestParam(defaultValue = "") String nome,
                      @RequestParam(defaultValue = "") String espansione,
                      @RequestParam(defaultValue = "") String tipoCarta,
                      @RequestParam(defaultValue = "") String tipoEnergia,
                      @RequestParam(defaultValue = "") String tipoAllenatore,
                      @RequestParam(defaultValue = "0") int page) {
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return "redirect:/login";
        }

        Page<CardResume> risultati;
        Pageable pageable = PageRequest.of(page, 24);

        risultati = cardRepository.cercaConFiltri(
                    nome, espansione, tipoCarta, tipoEnergia, tipoAllenatore, pageable);


        model.addAttribute("paginaCarte", risultati);
        model.addAttribute("pagina", page);
        model.addAttribute("title", "Add Card");
        model.addAttribute("carte", risultati.getContent());
        model.addAttribute("sets", setList);

        return "addCard";
    }

    // 2. Endpoint per il caricamento parziale (chiamato dal Form e da "Carica altre")
    @GetMapping("/addCard/cerca")
    public String cercaParziale(
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "") String espansione,
            @RequestParam(defaultValue = "") String tipoCarta,
            @RequestParam(defaultValue = "") String tipoEnergia,
            @RequestParam(defaultValue = "") String tipoAllenatore,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 24);
        Page<CardResume> risultati = cardRepository.cercaConFiltri(nome, espansione, tipoCarta, tipoEnergia,
                tipoAllenatore, pageable);

        model.addAttribute("paginaCarte", risultati);

        // Restituisce SOLO il frammento Thymeleaf invece di tutta la pagina
        return "addCard :: bloccoRisultati";
    }


    @GetMapping("/login")
    public String login() {
        return "login"; // Corrisponde a login.html in templates/
    }
}
