package com.samt.pokecarddex.model; // Adatta al tuo pacchetto

import java.io.Serializable;
import java.util.Objects;

public class UserCardId implements Serializable {
    private Long userId;
    private String cardId;

    // Costruttori obbligatori
    public UserCardId() {}

    public UserCardId(Long userId, String cardId) {
        this.userId = userId;
        this.cardId = cardId;
    }

    // Equals e HashCode obbligatori per permettere a Hibernate di fare i confronti
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCardId that = (UserCardId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(cardId, that.cardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, cardId);
    }

    // Getter e Setter
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
}
