package com.example.exercice4.service;
import com.example.exercice4.model.Evenement;
import com.example.exercice4.repository.EvenementRepository;
import org.springframework.http.HttpStatus;
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
        if(!event.isTitleValid() || !event.isLieuValid()) {
            throw new ResponseException(HttpStatus.UNPROCESSABLE_CONTENT, "Lieu et/ou Titre invalides");
        }
        return eventRepository.save(event);
    }

    public Evenement modifier(Long id, Evenement event) {
        if (!eventRepository.existsById(id)) {
            throw new ResponseException(HttpStatus.NOT_FOUND, "Evènement "+id+ " non trouvé");
        }
        event.setId(id);
        return eventRepository.save(event);
    }

    public void supprimer(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResponseException(HttpStatus.NOT_FOUND, "Suppression impossible, pas d'évènement avec cet id");
        }
        eventRepository.deleteById(id);
    }

    public List<Evenement> listerParLieu(String lieu) {
        return eventRepository.findByLieuOrderByDateAsc(lieu);
    }
}

