package org.example._9.DAOTests;

import jakarta.persistence.EntityManager;
import org.example._9.PersistenceManager;
import org.example._9.model.Genre;

public class GenreDAOTest {
    public static void GenreDAOTest() {
        EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager();

        entityManager.getTransaction().begin();

        Genre a = new Genre();
        a.setId(14112);
        a.setName("vdfgrdgr");
        entityManager.merge(a);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
