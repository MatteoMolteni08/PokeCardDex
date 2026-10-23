package com.samt.pokecarddex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(nullable = false)
    private String password;

    // --- METODI RICHIESTI DA USERDETAILS ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ritorna una lista vuota o i ruoli dell'utente (es. ROLE_USER)
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        // Indica a Spring Security quale campo usare come login
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // L'account non è scaduto
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // L'account non è bloccato
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Le credenziali non sono scadute
    }

    @Override
    public boolean isEnabled() {
        return true; // L'utente è attivo
    }
}

