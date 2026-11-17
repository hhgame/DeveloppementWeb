package com.example.exercice4.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Evenement {
    private String id;
    private LocalDate date;
    private String titre;
    private String lieu;


    public Evenement(String id, String date, String titre, String lieu) {
        this.id = id;
        this.titre = titre;
        this.lieu = lieu;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                .withLocale(Locale.getDefault());

        this.date = LocalDate.parse(date, formatter);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                .withLocale(Locale.getDefault());

        this.date = LocalDate.parse(date, formatter);
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(Locale.getDefault());
        return date.format(formatter);
    }

    public String getTitre() {
        return titre;
    }

    public String getLieu() {
        return lieu;
    }
}
