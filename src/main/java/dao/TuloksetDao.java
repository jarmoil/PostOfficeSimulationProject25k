package dao;

import simu.framework.IDao;
import entity.Tulokset;
import datasource.DatabaseConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.ArrayList;

public class TuloksetDao implements IDao {

    @Override
    public void tallenna(Tulokset tulokset) {
        EntityManager em = DatabaseConnection.getInstance();
        try {
            em.getTransaction().begin();
            em.persist(tulokset);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Database save failed: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Tulokset> lataaKaikki() {
        EntityManager em = DatabaseConnection.getInstance();
        try {
            return em.createQuery("SELECT t FROM Tulokset t", Tulokset.class)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new RuntimeException("Database load failed: " + e.getMessage(), e);
        }
    }
}