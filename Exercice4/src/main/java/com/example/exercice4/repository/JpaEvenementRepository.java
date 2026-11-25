package com.example.exercice4.repository;
import com.example.exercice4.model.Evenement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
@Repository
@Primary
@Transactional
public class JpaEvenementRepository implements EvenementRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Evenement> findAll() {
        return entityManager.createQuery("SELECT l FROM Evenement l ORDER BY l.id", Evenement
                        .class)
                .getResultList();
    }

    @Override
    public Optional<Evenement> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Evenement.class, id));
    }

    @Override
    public boolean existsById(Long id) {
        return entityManager.find(Evenement.class, id) != null;
    }

    @Override
    public Evenement save(Evenement event) {
        if (event.getId() == null) {
            entityManager.persist(event);
            return event;
        }
        return entityManager.merge(event);
    }

    @Override
    public void deleteById(Long id) {
        Evenement event = entityManager.find(Evenement.class, id);
        if (event != null) {
            entityManager.remove(event);
        }
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(l) FROM Evenement l", Long.class)
                .getSingleResult();
    }
}*/
