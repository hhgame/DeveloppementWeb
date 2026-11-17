package com.example.exercice4.repository;
import com.example.exercice4.model.Evenement;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientEvenementRepository implements EvenementRepository {

    private final JdbcClient jdbc;

    public JdbcClientEvenementRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Evenement> findAll() {
        return jdbc.sql("SELECT id, titre, lieu, date FROM evenement ORDER BY id")
                .query(Evenement.class)
                .list();
    }

    @Override
    public Optional<Evenement> findById(Long id) {
        return jdbc.sql("SELECT id, titre, lieu, date FROM evenement WHERE id = ?")
                .param(id)
                .query(Evenement.class)
                .optional();
    }

    @Override
    public boolean existsById(Long id) {
        return jdbc.sql("SELECT COUNT(*) FROM evenement WHERE id = ?")
                .param(id)
                .query(Long.class)
                .single() > 0;
    }

    @Override
    public Evenement save(Evenement event) {
        if (event.getId() == null) {
            KeyHolder cle = new GeneratedKeyHolder();
            jdbc.sql("INSERT INTO evenement (titre, lieu, date) VALUES (?, ?, ?)")
                    .params(event.getTitre(), event.getLieu(), event.getDate())
                    .update(cle);
            event.setId(cle.getKeyAs(Long.class));
        } else {
            jdbc.sql("UPDATE evenement SET titre = ?, lieu = ?, date = ? WHERE id = ?")
                    .params(event.getTitre(), event.getLieu(), event.getDate(), event.getId())
                    .update();
        }
        return event;
    }

    @Override
    public void deleteById(Long id) {
        jdbc.sql("DELETE FROM evenement WHERE id = ?")
                .param(id)
                .update();
    }

    @Override
    public long count() {
        return jdbc.sql("SELECT COUNT(*) FROM evenement")
                .query(Long.class)
                .single();
    }
}

