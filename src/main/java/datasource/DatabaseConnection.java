package datasource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Manages database connections using JPA/Hibernate EntityManager.
 * Implements a singleton pattern to ensure a single database connection instance throughout the application.
 */
public class DatabaseConnection {

    /**
     * Static instance of EntityManagerFactory for creating EntityManager instances.
     */
    private static EntityManagerFactory emf = null;

    /**
     * Static instance of EntityManager for handling database operations.
     */
    private static EntityManager em = null;

    /**
     * Returns a singleton instance of EntityManager.
     * Creates new EntityManagerFactory and EntityManager instances if they don't exist.
     *
     * @return EntityManager instance for database operations
     */
    public static EntityManager getInstance() {
        if (em == null) {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("simulaattori_db");
            }
            em = emf.createEntityManager();
        }
        return em;
    }

    /**
     * Tests the database connection by creating and closing an EntityManagerFactory.
     * Used for verifying database connectivity.
     */
    public static void tester() {
        EntityManagerFactory emf_tester = Persistence.createEntityManagerFactory("simulaattori_db");
        emf_tester.close();
    }
}
