package com.example.exercice4.repository;
import com.example.exercice4.model.Evenement;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcEvenementRepository implements EvenementRepository {

    private final DataSource dataSource;

    public JdbcEvenementRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Evenement> findAll() {
        String sql = "SELECT id, titre, lieu, date FROM evenement ORDER BY id";
        try (Connection connexion = dataSource.getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql);
             ResultSet resultat = requete.executeQuery()) {
            List<Evenement> events = new ArrayList<>();
            while (resultat.next()) {
                events.add(versEvenement(resultat));
            }
            return events;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Evenement> findById(Long id) {
        String sql = "SELECT id, titre, lieu, date FROM evenement WHERE id = ?";
        try (Connection connexion = dataSource.getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {
            requete.setLong(1, id);
            try (ResultSet resultat = requete.executeQuery()) {
                if (resultat.next()) {
                    return Optional.of(versEvenement(resultat));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Evenement save(Evenement event) {
        if (event.getId() == null) {
            return inserer(event);
        }
        return mettreAJour(event);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM evenement WHERE id = ?";
        try (Connection connexion = dataSource.getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {
            requete.setLong(1, id);
            requete.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM evenement";
        try (Connection connexion = dataSource.getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql);
             ResultSet resultat = requete.executeQuery()) {
            resultat.next();
            return resultat.getLong(1);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private Evenement inserer(Evenement event) {
        String sql = "INSERT INTO evenement (titre, lieu, date) VALUES (?, ?, ?)";
        try (Connection connexion = dataSource.getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            remplir(requete, event);
            requete.executeUpdate();
            try (ResultSet cles = requete.getGeneratedKeys()) {
                cles.next();
                event.setId(cles.getLong(1));
            }
            return event;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private Evenement mettreAJour(Evenement event) {
        String sql = "UPDATE evenement SET titre = ?, lieu = ?, date = ? WHERE id = ?";
        try (Connection connexion = dataSource.getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {
            remplir(requete, event);
            requete.setLong(4, event.getId());
            requete.executeUpdate();
            return event;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void remplir(PreparedStatement requete, Evenement event) throws SQLException {
        requete.setString(1, event.getTitre());
        requete.setString(2, event.getLieu());
        requete.setObject(3, event.getDate(), Types.DATE);
    }

    private Evenement versEvenement(ResultSet resultat) throws SQLException {
        Evenement livre = new Evenement(
                resultat.getString("titre"),
                resultat.getString("lieu"),
                resultat.getString("date")
        );
        livre.setId(resultat.getLong("id"));
        return livre;
    }
}

