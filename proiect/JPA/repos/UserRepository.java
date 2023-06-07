package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.JPA.model.User;

import java.util.List;

public class UserRepository extends AbstractRepository<User, Integer> {
    public UserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    public void create(User user) {
        entityManager.getTransaction().begin();
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            List<User> users = findByName(user.getUsername());
            if (users.isEmpty()) {
                entityManager.persist(user);
            } else {
                user.setId(users.get(0).getId());
            }
        }
        entityManager.getTransaction().commit();
    }

    public List<User> findByName(String namePattern) {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findByName", User.class);
        query.setParameter("username", "%" + namePattern + "%");
        return query.getResultList();
    }

}
