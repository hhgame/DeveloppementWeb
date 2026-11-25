package com.example.exercice4.service;

import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

public class ResponseException extends RuntimeException {
    private HttpStatus status;
    public ResponseException(HttpStatus statusCode, String message) {
        super(message);
        this.status = statusCode;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
