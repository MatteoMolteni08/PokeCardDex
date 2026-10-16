package com.samt.pokecarddex.model;

import jakarta.persistence.*;

@Entity
@Table(name = "card")
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "card_id", nullable = false)
    private String cardId; // Memorizza la stringa dell'ID TCGdex (es: "swsh3-136")

    // Getter e Setter
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
}
