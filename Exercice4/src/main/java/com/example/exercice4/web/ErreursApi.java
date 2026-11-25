package com.example.exercice4.web;

import com.example.exercice4.service.EvenementIntrouvableException;
import com.example.exercice4.service.PlannerAvecEvenementException;
import com.example.exercice4.service.PlannerIntrouvableException;
import com.example.exercice4.service.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {EvenementRestController.class, PlannerRestController.class})
public class ErreursApi {

    @ExceptionHandler(EvenementIntrouvableException.class)
    public ProblemDetail livreIntrouvable(EvenementIntrouvableException e) {
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        probleme.setTitle("Evénement introuvable");
        return probleme;
    }

    @ExceptionHandler(ResponseException.class)
    public ProblemDetail responseException(ResponseException e) {
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
        return probleme;
    }

    @ExceptionHandler(PlannerIntrouvableException.class)
    public ProblemDetail plannerIntrouvable(PlannerIntrouvableException e) {
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        probleme.setTitle("Planner introuvable");
        return probleme;
    }

    @ExceptionHandler(PlannerAvecEvenementException.class)
    public ProblemDetail plannerAvecEvenements(PlannerAvecEvenementException e) {
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        probleme.setTitle("Planner encore utilisé");
        return probleme;
    }
}

