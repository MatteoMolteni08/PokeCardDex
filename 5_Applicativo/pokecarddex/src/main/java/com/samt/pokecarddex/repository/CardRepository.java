package com.samt.pokecarddex.repository;

import lombok.Data;
import lombok.Getter;
import net.tcgdex.sdk.TCGdex;
import net.tcgdex.sdk.models.Card;
import net.tcgdex.sdk.models.CardResume;
import net.tcgdex.sdk.models.SerieResume;
import net.tcgdex.sdk.models.SetResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;

@Data
@Getter
public class CardRepository {
    private TCGdex tcgdex = new TCGdex("it");

    private ArrayList<CardResume> cards;
    private ArrayList<SerieResume> series;
    private ArrayList<SetResume> sets;
    private CardResume[] provCard;

    public CardRepository() {
        cards = new ArrayList<>();
        sets = new ArrayList<>(Arrays.stream(tcgdex.fetchSets()).toList());
        series = new ArrayList<>(Arrays.stream(tcgdex.fetchSeries()).toList());
    }

    public Page<CardResume> cercaConFiltri(String nome, String espansione,
                                           String tipoCarta, String tipoEnergia,
                                           String tipoAllenatore, Pageable pageable) {
        if (nome.isBlank() && espansione.isBlank() && tipoCarta.isBlank() && tipoEnergia.isBlank()
                && tipoAllenatore.isBlank()) {return Page.empty(pageable);}
        if (espansione.isBlank()) {
            for (SetResume set : sets) {
                if (set.getName() == nome) {
                    provCard = (CardResume[]) set.getFullSet().getCards().toArray();
                    break;
                }
            }
        } else {
            provCard = tcgdex.fetchCards();
            if (provCard == null) {provCard = tcgdex.fetchCards();}
        }


        for (CardResume card : provCard) {
            if (!card.getName().toLowerCase().contains(nome.toLowerCase())) {
                continue;
            }

            if (card.getFullCard().getCategory().equals(tipoCarta) && !tipoCarta.isBlank()) {
                {
                    switch (card.getFullCard().getCategory()) {
                        case "Pokemon":
                            if (card.getFullCard().getTypes().get(0) != tipoEnergia
                                    && card.getFullCard().getTypes().get(1) != tipoEnergia
                                    && !tipoEnergia.isBlank()) {
                                continue;
                            }
                            break;
                        case "Energy":
                            if (card.getFullCard().getEnergyType() != tipoEnergia &&
                                    !tipoEnergia.isBlank()) {
                                continue;
                            }
                            break;
                        case "Trainer":
                            if (tipoAllenatore != "") {
                                if (card.getFullCard().getTrainerType() != tipoAllenatore) {
                                    continue;
                                }
                            }
                            break;
                    }
                }
            }
            if (card.getFullCard().getSet().getFullSet().getSerie().getId().equals("tcgp")) {
                continue;
            }

            cards.add(card);
        }
        return new PageImpl<CardResume>(cards, pageable, cards.size());
    }
}
