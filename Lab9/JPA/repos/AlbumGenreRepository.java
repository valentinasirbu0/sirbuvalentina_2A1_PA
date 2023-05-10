package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import org.example.JPA.model.AlbumGenre;

public class AlbumGenreRepository extends AbstractRepository<AlbumGenre, Long> {
    public AlbumGenreRepository(EntityManager entityManager) {
        super(entityManager, AlbumGenre.class);
    }

    public void create(AlbumGenre albumGenre) {
        entityManager.getTransaction().begin();
        entityManager.persist(albumGenre);
        entityManager.getTransaction().commit();
    }
}
