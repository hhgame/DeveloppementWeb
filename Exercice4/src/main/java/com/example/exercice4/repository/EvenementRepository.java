package com.example.exercice4.repository;

import com.example.exercice4.model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    /*public List<Evenement> findAll();
    public Optional<Evenement> findById(Long id);

    public boolean existsById(Long id);

    public Evenement save(Evenement event);
    public void deleteById(Long id);

    public long count();*/
    public List<Evenement> findByLieuOrderByDateAsc(String lieu) ;
}
