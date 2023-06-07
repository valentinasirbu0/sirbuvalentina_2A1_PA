package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.JPA.model.Song;
import org.example.JPA.model.User;
import org.example.JPA.model.UserFavourite;

import java.util.List;

public class UsersFavouriteRepository extends AbstractRepository<UserFavourite, Long> {
    public UsersFavouriteRepository(EntityManager entityManager) {
        super(entityManager, UserFavourite.class);
    }

    public void create(UserFavourite userFavourite) {
        entityManager.getTransaction().begin();

        // Check if the combination already exists
        User user = userFavourite.getUser();
        Song song = userFavourite.getSong();
        if (user == null || song == null) {
            entityManager.getTransaction().rollback();
            return;
        }
        UserFavourite compositeKey = new UserFavourite(user, song);
        UserFavourite existingUserfavourite = entityManager.find(UserFavourite.class, compositeKey);
        if (existingUserfavourite != null) {
            entityManager.getTransaction().rollback();
            return;
        }

        entityManager.persist(userFavourite);
        entityManager.getTransaction().commit();
    }

    public List<UserFavourite> getAllFavourites(User user) {
        TypedQuery<UserFavourite> query = entityManager.createNamedQuery("UserFavourite.findAllFavourites", UserFavourite.class);
        query.setParameter("userId", user.getId());
        return query.getResultList();
    }
}
