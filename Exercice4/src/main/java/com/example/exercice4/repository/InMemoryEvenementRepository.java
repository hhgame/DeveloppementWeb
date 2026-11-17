package com.example.exercice4.repository;

import com.example.exercice4.model.Evenement;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryEvenementRepository {
    private final Map<Long, Evenement> events = new HashMap<>();
    private long sequence = 0;

    public InMemoryEvenementRepository() {
        enregistrer(new Evenement("12 novembre 2026", "Event1", "Quelque part"));
        enregistrer(new Evenement("2 juin 2027", "Event2", "où"));
        enregistrer(new Evenement("26 février 2028", "Event3", "ici"));
    }

    public List<Evenement> findAll() {
        return events.values().stream()
                .sorted(Comparator.comparing(Evenement::getId))
                .toList();
    }

    public Optional<Evenement> findById(Long id) {
        return Optional.ofNullable(events.get(id));
    }

    public boolean existsById(Long id) {
        return events.containsKey(id);
    }

    public Evenement save(Evenement event) {
        return enregistrer(event);
    }

    public void deleteById(Long id) {
        events.remove(id);
    }

    public long count() {
        return events.size();
    }

    private Evenement enregistrer(Evenement event) {
        events.put(event.getId(), event);
        return event;
    }
}
