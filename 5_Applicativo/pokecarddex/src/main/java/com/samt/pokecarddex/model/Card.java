package com.samt.pokecarddex.model;

import net.tcgdex.sdk.TCGdex;
import org.springframework.cache.annotation.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Card {
    private static final Logger log = LoggerFactory.getLogger(Card.class);
    private final TCGdex tcgdex;

    public Card() {
        // Inizializza l'SDK ufficiale impostando la lingua predefinita in italiano
        this.tcgdex = new TCGdex("it");
    }

    // Integrazione perfetta con la Cache di Spring vista prima
    @Cacheable(value = "pokemonCards", key = "#cardId")
    public net.tcgdex.sdk.models.Card getCardById(String cardId) {
        log.info("Chiamata SDK a TCGdex per l'ID: {}", cardId);
        try {
            // L'SDK interroga il server e istanzia automaticamente l'oggetto Card
            return this.tcgdex.fetchCard(cardId);
        } catch (Exception e) {
            log.error("Errore durante il recupero della carta tramite SDK", e);
            return null;
        }
    }
}
