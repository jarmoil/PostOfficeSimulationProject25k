package dao;

import simu.framework.IDao;
import entity.Tulokset;
import datasource.DatabaseConnection;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;



public class TuloksetDao implements IDao {

    @Override
    public void tallenna(Tulokset tulokset) {
        EntityManager em = DatabaseConnection.getInstance();
        try {
            em.getTransaction().begin();
            // Clear the persistence context first
            em.clear();
            // Detach the entity if it's managed
            if (em.contains(tulokset)) {
                em.detach(tulokset);
            }
            // Persist the new entity
            em.persist(tulokset);
            em.getTransaction().commit();
        } catch (Exception e) {
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

    @Override
    public void truncateAll() {
        EntityManager em = DatabaseConnection.getInstance();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE simuloinnit").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE erityistapaukset").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE noutolaheta").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE pakettiautomaatti").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE palvelunvalinta").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE palveluaika_ika").executeUpdate();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Database truncate failed: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteTulos(Tulokset tulos) {
        EntityManager em = DatabaseConnection.getInstance();
        try {
            em.getTransaction().begin();
            tulos = em.merge(tulos);  // Ensure that the entity is managed
            em.remove(tulos);         // Remove the entity
            em.getTransaction().commit();  // Commit the transaction
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Rollback if something fails
            }
            return false;
        }
    }
}