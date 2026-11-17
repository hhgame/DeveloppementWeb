package com.example.exercice4.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

    @GetMapping("/")
    public String accueil() {
        return "redirect:/events";
    }
}