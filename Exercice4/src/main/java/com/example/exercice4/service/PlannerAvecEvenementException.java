package com.example.exercice4.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlannerAvecEvenementException extends RuntimeException {

    public PlannerAvecEvenementException(Long id) {
        super("Le planner " + id + " a encore des évènements");
    }
}
