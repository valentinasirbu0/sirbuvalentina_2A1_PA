package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.JPA.model.Genre;

import java.util.List;

public class GenreRepository extends AbstractRepository<Genre, Integer> {
    public GenreRepository(EntityManager entityManager) {
        super(entityManager, Genre.class);
    }

    public void create(Genre genre) {
        entityManager.getTransaction().begin();
        if (genre.getName() == null) {
            entityManager.persist(genre);
        }else {
            List<Genre> genres = findByName(genre.getName());
            if (genres.isEmpty()) {
                entityManager.persist(genre);
            } else {
                genre.setId(genres.get(0).getId());
            }
        }
        entityManager.getTransaction().commit();
    }

    public Genre findById(Integer id) {
        return entityManager.find(Genre.class, id);
    }

    public List<Genre> findByName(String namePattern) {
        TypedQuery<Genre> query = entityManager.createNamedQuery("Genre.findByName", Genre.class);
        query.setParameter("name", "%" + namePattern + "%");
        return query.getResultList();
    }
}
