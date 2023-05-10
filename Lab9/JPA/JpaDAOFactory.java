package org.example.JPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.DAOFactory;
import org.example.JPA.DataSets.CSVData;

public class JpaDAOFactory implements DAOFactory {
    private static final String PERSISTENCE_UNIT_NAME = "ExamplePU";
    private static EntityManagerFactory entityManagerFactory;

    public JpaDAOFactory() {}

    public static synchronized EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return entityManagerFactory.createEntityManager();
    }

    public static synchronized void closeEntityManagerFactory() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return entityManagerFactory;
    }

    @Override
    public void setData() {
        CSVData data = new CSVData();
    }
}