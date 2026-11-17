package com.example.exercice4.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EvenementIntrouvableException extends RuntimeException {

    public EvenementIntrouvableException(Long id) {
        super("Evenement introuvable : " + id);
    }
}

