package com.samt.pokecarddex.repository;

import com.samt.pokecarddex.model.UserCard;
import com.samt.pokecarddex.model.UserCardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, UserCardId> {
    // Recupera le ultime 5 carte inserite dall'utente, ordinate dall'ultima alla prima
    List<UserCard> findTop5ByUserId(Long userId);
}
