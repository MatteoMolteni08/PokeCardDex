package com.samt.pokecarddex.controller;

// 1. IMPORTANTE: Forza l'import del TUO modello User e rimuovi quello di Spring Security se presente
import com.samt.pokecarddex.model.User;
import com.samt.pokecarddex.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // 2. DICHIARA LE VARIABILI GLOBALI MANCANTI
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;

    // 3. INIETTA LE VARIABILI TRAMITE IL COSTRUTTORE
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String nome,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {

        // Ora userRepository funzionerà senza errori
        if (userRepository.findByNomeOrEmail(nome, email).isPresent()) {
            model.addAttribute("error", "Nome utente o Email già utilizzati!");
            return "signup";
        }

        // Ora Java capirà che si tratta del tuo modello User con i relativi setter
        User newUser = new User();
        newUser.setNome(nome);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password)); // Cifratura funzionante

        userRepository.save(newUser);

        return "redirect:/login?success";
    }

    /*@PostMapping("/login")
    public String loginUser(
            @RequestParam String username,
            @RequestParam String password){
        if (userRepository.findByNomeOrEmail(username, username).isPresent()) {
            if (passwordEncoder.matches(password, ))
        }
    }*/
}
