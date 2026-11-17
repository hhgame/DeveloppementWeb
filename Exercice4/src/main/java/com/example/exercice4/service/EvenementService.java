package com.example.exercice4.service;
import com.example.exercice4.model.Evenement;
import com.example.exercice4.repository.EvenementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvenementService {

    private final EvenementRepository eventRepository;

    public EvenementService(EvenementRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Evenement> lister() {
        return eventRepository.findAll();
    }

    public Evenement trouver(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EvenementIntrouvableException(id));
    }

    public Evenement ajouter(Evenement event) {
        event.setId(null);
        return eventRepository.save(event);
    }

    public Evenement modifier(Long id, Evenement event) {
        if (!eventRepository.existsById(id)) {
            throw new EvenementIntrouvableException(id);
        }
        event.setId(id);
        return eventRepository.save(event);
    }

    public void supprimer(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EvenementIntrouvableException(id);
        }
        eventRepository.deleteById(id);
    }
}

