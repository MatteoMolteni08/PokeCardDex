package com.samt.pokecarddex.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.samt.pokecarddex.service.UserService;

import javax.swing.*;

@Configuration
public class SecurityConfig {

    // Encoder obbligatorio per gestire le password in modo sicuro nel DB
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Colleghiamo il tuo UserService e il PasswordEncoder a Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Sfrutta la configurazione globale interna di Spring Security.
        // L'errore di compilazione scompare del tutto perché non crei manualmente il Provider.
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Permette l'accesso pubblico esplicito alle pagine HTML di login e signup
                        .requestMatchers("/login", "/signup").permitAll()
                        // Permette l'accesso a tutte le risorse statiche standard (CSS, JS)
                        .requestMatchers("/css/securityStyle.css", "/js/signupScript.js", "/images/sfondocarte.png").permitAll()
                        // Qualsiasi altra richiesta richiederà l'autenticazione
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }


}
