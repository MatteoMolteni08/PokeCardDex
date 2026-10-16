package com.samt.pokecarddex.repository;

import com.samt.pokecarddex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNomeOrEmail(String nome, String email);

    // Metodi di query derivati per i controlli di unicità
    boolean existsByEmail(String email);
    boolean existsByNome(String nome);
}