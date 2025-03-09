package dao;

import simu.framework.IDao;
import entity.*;
import datasource.DatabaseConnection;
import java.util.List;
import jakarta.persistence.EntityManager;


public class TuloksetDao implements IDao {

    @Override
    public void tallenna(Tulokset tulokset) {
        try {
            EntityManager em = DatabaseConnection.getInstance();
            em.getTransaction().begin();
            em.persist(tulokset);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tulokset> lataaKaikki() {
        EntityManager em = DatabaseConnection.getInstance();
        List<Tulokset> tulokset = em.createQuery("SELECT t FROM Tulokset t", Tulokset.class).getResultList();
        return tulokset;
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