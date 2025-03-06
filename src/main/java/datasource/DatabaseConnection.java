package datasource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConnection {
    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;

    public static EntityManager getInstance() {
        if (em == null) {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("simulaattori_db");
            }
            em = emf.createEntityManager();
        }
        return em;
    }

    public static void tester() {
        EntityManagerFactory emf_tester = Persistence.createEntityManagerFactory("simulaattori_db");
        emf_tester.close();
    }
}
