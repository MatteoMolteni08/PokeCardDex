package com.samt.pokecarddex.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "card")
@IdClass(UserCardId.class) // Colleghiamo la chiave composta
@Data
public class UserCard {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "card_id", nullable = false)
    private String cardId; // Memorizza la stringa dell'ID TCGdex (es: "swsh3-136")

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
