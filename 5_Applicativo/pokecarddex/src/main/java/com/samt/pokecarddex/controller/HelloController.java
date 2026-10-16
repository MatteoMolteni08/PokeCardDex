package com.samt.pokecarddex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Corrisponde a login.html in templates/
    }
}
