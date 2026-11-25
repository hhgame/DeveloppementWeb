package com.example.exercice4.service;

import com.example.exercice4.model.Evenement;
import com.example.exercice4.model.Planner;
import com.example.exercice4.repository.EvenementRepository;
import com.example.exercice4.repository.PlannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final EvenementRepository eventRepository;

    public PlannerService(PlannerRepository plannerRepository, EvenementRepository eventRepository) {
        this.plannerRepository = plannerRepository;
        this.eventRepository = eventRepository;
    }

    public List<Planner> lister() {
        return plannerRepository.findAll();
    }

    public List<Planner> rechercher(String motCle) {
        if (motCle == null || motCle.isBlank()) {
            return plannerRepository.findAll();
        }
        String m = motCle.trim();
        return plannerRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(m, m);
    }

    public Planner trouver(Long id) {
        return plannerRepository.findById(id)
                .orElseThrow(() -> new PlannerIntrouvableException(id));
    }

    public List<Evenement> evenements(Long id) {
        return trouver(id).getEvenements();
    }

    public Planner ajouter(Planner planner) {
        planner.setId(null);
        return plannerRepository.save(planner);
    }

    public Planner modifier(Long id, Planner planner) {
        if (!plannerRepository.existsById(id)) {
            throw new PlannerIntrouvableException(id);
        }
        planner.setId(id);
        return plannerRepository.save(planner);
    }

    // On refuse de supprimer un auteur qui a encore des livres :
    // sa clé étrangère est référencée par la table LIVRE.
    public void supprimer(Long id) {
        if (!plannerRepository.existsById(id)) {
            throw new PlannerIntrouvableException(id);
        }
        if (eventRepository.existsByPlannerId(id)) {
            throw new PlannerAvecEvenementException(id);
        }
        plannerRepository.deleteById(id);
    }
}

