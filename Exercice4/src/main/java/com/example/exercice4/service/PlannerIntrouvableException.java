package com.example.exercice4.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlannerIntrouvableException extends RuntimeException {

    public PlannerIntrouvableException(Long id) {
        super("Planner introuvable : " + id);
    }
}

