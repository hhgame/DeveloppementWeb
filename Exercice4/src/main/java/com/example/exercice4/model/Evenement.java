package com.example.exercice4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String titre;
    private String lieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id")
    private Planner planner;


    public Evenement() {}

    public Evenement(String titre, String lieu, String dateStr) {
        this.titre = titre;
        this.lieu = lieu;
        this.date = parseDate(dateStr);
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null) return null;

        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e1) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
                return LocalDate.parse(dateStr, formatter);
            } catch (Exception e2) {
                throw new IllegalArgumentException("Format de date non reconnu : " + dateStr);
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDateFormatee() {
        if (this.date == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(Locale.getDefault());
        return this.date.format(formatter);
    }

    public boolean isTitleValid() {
        return this.titre != null && this.titre != "";
    }

    public boolean isLieuValid() {
        return this.lieu != null && this.lieu != "";
    }

    public Planner getPlanner() {
        return this.planner;
    }
}