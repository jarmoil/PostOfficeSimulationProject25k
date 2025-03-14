package dao;

import simu.framework.IDao;
import entity.Tulokset;
import datasource.DatabaseConnection;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;


/**
 * Data Access Object class for managing simulation results (Tulokset) in the database.
 * Implements the IDao interface to provide CRUD operations for simulation results.
 * Uses JPA/Hibernate for database operations through EntityManager.
 */
public class TuloksetDao implements IDao {

    /**
     * Saves a new simulation result to the database.
     * The method clears the persistence context and detaches any managed entity before persisting
     * to ensure clean state.
     *
     * @param tulokset The simulation results object to be saved
     * @throws RuntimeException if the database operation fails
     */
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

    /**
     * Retrieves all simulation results from the database.
     *
     * @return List of all Tulokset objects stored in the database
     * @throws RuntimeException if the database operation fails
     */
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

    /**
     * Truncates (removes all records) from all simulation-related tables in the database.
     * Tables affected: simuloinnit, erityistapaukset, noutolaheta, pakettiautomaatti,
     * palvelunvalinta, and palveluaika_ika.
     *
     * @throws RuntimeException if the database operation fails
     */
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

    /**
     * Deletes a specific simulation result from the database.
     * The method merges the entity first to ensure it's in managed state before removal.
     *
     * @param tulos The simulation result object to be deleted
     * @return true if deletion was successful, false if it failed
     */
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