package org.example.JPA.repos;

import jakarta.persistence.EntityManager;

public abstract class AbstractRepository<T, ID> {
    protected final EntityManager entityManager;
    private final Class<T> entityClass;

    public AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public T findById(Integer id) {
        return entityManager.find(entityClass, id);
    }

    public T update(T entity) {
        entityManager.getTransaction().begin();
        T mergedEntity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return mergedEntity;
    }

    public void delete(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }
}

