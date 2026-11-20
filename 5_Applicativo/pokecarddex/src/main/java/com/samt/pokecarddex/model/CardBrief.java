package com.samt.pokecarddex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.tcgdex.sdk.models.CardResume;

@Data
@AllArgsConstructor
@Getter
public class CardBrief {
    private String id;
    private String localId;
    private String name;
    private String image;

    // Metodo helper per avere subito l'URL completo dell'immagine
    public String getImageUrl() {
        if (this.image == null || this.image.isBlank()) {
            return "/img/retro-carta.png"; // Immagine placeholder se mancante
        }
        return this.image + "/high.webp";
    }
}
