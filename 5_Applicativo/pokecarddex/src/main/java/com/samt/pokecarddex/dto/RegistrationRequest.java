package com.samt.pokecarddex.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {
    @NotBlank(message = "L'email è obbligatoria.")
    @Email(message = "Inserisci un indirizzo email valido.")
    private String email;

    @NotBlank(message = "Lo username è obbligatorio.")
    @Size(min = 3, max = 20, message = "Lo username deve essere compreso tra 3 e 20 caratteri.")
    private String username;

    @NotBlank(message = "La password è obbligatoria.")
    @Size(min = 8, message = "La password deve contenere almeno 8 caratteri.")
    private String password;

    // Getter e Setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
