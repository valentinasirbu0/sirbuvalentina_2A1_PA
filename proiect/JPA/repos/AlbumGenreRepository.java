package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import org.example.JPA.model.Album;
import org.example.JPA.model.AlbumGenre;
import org.example.JPA.model.Genre;

public class AlbumGenreRepository extends AbstractRepository<AlbumGenre, Long> {
    public AlbumGenreRepository(EntityManager entityManager) {
        super(entityManager, AlbumGenre.class);
    }

    public void create(AlbumGenre albumGenre) {
        entityManager.getTransaction().begin();

        // Check if the combination already exists
        Album album = albumGenre.getAlbum();
        Genre genre = albumGenre.getGenre();
        if (album == null || genre == null) {
            entityManager.getTransaction().rollback();
            // Handle the case where either album or genre is null
            // You can throw an exception, return a result, or perform any other action
            return;
        }
        AlbumGenre compositeKey = new AlbumGenre(album, genre);
        AlbumGenre existingAlbumGenre = entityManager.find(AlbumGenre.class, compositeKey);
        if (existingAlbumGenre != null) {
            // Combination already exists, handle accordingly
            entityManager.getTransaction().rollback();
            // You can throw an exception, return a result, or perform any other action
            return;
        }

        // Combination does not exist, persist the entity
        entityManager.persist(albumGenre);
        entityManager.getTransaction().commit();

    }


}
