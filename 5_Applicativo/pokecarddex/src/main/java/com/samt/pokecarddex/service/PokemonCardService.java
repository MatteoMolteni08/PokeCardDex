package com.samt.pokecarddex.service;

import com.samt.pokecarddex.model.Card;
import com.samt.pokecarddex.model.UserCard;
import com.samt.pokecarddex.repository.UserCardRepository;
import net.tcgdex.sdk.TCGdex;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.HttpClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PokemonCardService {

    private static final Logger log = LoggerFactory.getLogger(PokemonCardService.class);
    private final TCGdex tcgdex;
    private final UserCardRepository userCardRepository;

    // Iniettiamo sia l'SDK di TCGdex che il repository MySQL nello strato Service
    public PokemonCardService(UserCardRepository userCardRepository) {
        this.userCardRepository = userCardRepository;
        this.tcgdex = new TCGdex("it");
    }

    // Mantieni il metodo atomico protetto dalla Cache di Spring
    @Cacheable(value = "pokemonCards", key = "#cardId")
    public net.tcgdex.sdk.models.Card getCardById(String cardId) {
        log.info("Chiamata SDK a TCGdex per l'ID: {}", cardId);
        try {
            return this.tcgdex.fetchCard(cardId);
        } catch (Exception e) {
            log.error("Errore durante il recupero della singola carta", e);
            return null;
        }
    }

    /**
     * NUOVA FUNZIONE DI BUSINESS: Recupera i dati dal DB e restituisce le carte mappate.
     * Isola completamente il controller dalle logiche di persistenza e cicli di rete.
     */
    /*public List<net.tcgdex.sdk.models.Card> getLastFiveCardsForUser(Long userId) {
        // 1. Recupera i record grezzi da MySQL
        List<UserCard> userCards = userCardRepository.findTop5ByUserIdOrderByIdDesc(userId);

        List<net.tcgdex.sdk.models.Card> fullCardsList = new ArrayList<>();

        // 2. Compila la lista richiamando il metodo interno (che beneficia automaticamente della cache)
        for (UserCard uc : userCards) {
            net.tcgdex.sdk.models.Card card = this.getCardById(uc.getCardId());
            if (card != null) {
                fullCardsList.add(card);
            }
        }

        return fullCardsList;
    }*/
    public List<Map<String, String>> getLastFiveCardsForUser(Long userId) {
        List<UserCard> userCards = userCardRepository.findTop5ByUserId(userId);
        List<Map<String, String>> safeCards = new ArrayList<>();

        for (UserCard uc : userCards) {
            net.tcgdex.sdk.models.Card card = this.getCardById(uc.getCardId());
            if (card != null && card.getImage() != null) {
                Map<String, String> c = new HashMap<>();
                c.put("name", card.getName());
                // Generiamo l'URL dell'immagine definitivo già pronto per l'HTML
                c.put("imageUrl", card.getImage() + "/high.png");
                safeCards.add(c);
            }
        }
        return safeCards;
    }
}
