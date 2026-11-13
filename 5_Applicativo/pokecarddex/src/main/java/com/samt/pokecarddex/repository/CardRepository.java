package com.samt.pokecarddex.repository;

import lombok.Data;
import lombok.Getter;
import net.tcgdex.sdk.TCGdex;
import net.tcgdex.sdk.models.Card;
import net.tcgdex.sdk.models.CardResume;
import net.tcgdex.sdk.models.SetResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

@Data
@Getter
public class CardRepository {
    private TCGdex tcgdex = new TCGdex("it");

    private ArrayList<SetResume> sets;
    private ArrayList<Card> cards;

    private SetResume[] set;
    private CardResume[] provCard;

    public CardRepository() {
        sets = new ArrayList<>();
        cards = new ArrayList<Card>();
        set = tcgdex.fetchSets();
        provCard = tcgdex.fetchCards();
    }

    public Page<Card> cercaConFiltri(String nome, String espansione,
                                     String tipoCarta, String tipoEnergia,
                                     String tipoAllenatore, Pageable pageable)
    {

        if (espansione != ""){
            for (SetResume set : sets){
                if (set.getName() == nome){
                    provCard = tcgdex.fetchSet(set.getId()).getCards().toArray(CardResume[]::new);
                    break;
                }
            }
        }else {
            provCard = tcgdex.fetchCards();
        }
        for (CardResume card : provCard){
            if (card.getName().contains(nome)){
                cards.add(tcgdex.fetchCard(card.getId()));
            }
        }
        if (tipoCarta != ""){
            for (Card card : cards) {
                if (card.getCategory() != tipoCarta){
                    cards.remove((card));
                }else {
                    switch (card.getCategory()){
                        case "Pokemon":
                            if (card.getTypes().get(0) != tipoEnergia && card.getTypes().get(1) != tipoEnergia){
                                cards.remove((card));
                            }
                            break;
                        case "Energy":
                            if (card.getEnergyType() != tipoEnergia){
                                cards.remove((card));
                            }
                            break;
                        case "Trainer":
                            if (card.getTrainerType() != tipoAllenatore){
                                cards.remove((card));
                            }
                            break;
                    }
                }
            }
        }

        return new PageImpl<Card>(cards, pageable, cards.size());
    }
}
