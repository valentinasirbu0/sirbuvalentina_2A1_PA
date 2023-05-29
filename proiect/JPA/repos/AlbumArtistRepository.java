package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import org.example.JPA.model.*;

public class AlbumArtistRepository extends AbstractRepository<AlbumArtist,Long> {
    public AlbumArtistRepository(EntityManager entityManager) {
        super(entityManager, AlbumArtist.class);
    }

    public void create(AlbumArtist albumArtist) {
        entityManager.getTransaction().begin();

        // Check if the combination already exists
        Album album = albumArtist.getAlbum();
        Artist artist = albumArtist.getArtist();
        if (album == null || artist == null) {
            entityManager.getTransaction().rollback();
            // Handle the case where either album or genre is null
            // You can throw an exception, return a result, or perform any other action
            return;
        }
        AlbumArtist compositeKey = new AlbumArtist(album, artist);
        AlbumArtist existingAlbumArtist = entityManager.find(AlbumArtist.class, compositeKey);
        if (existingAlbumArtist != null) {
            // Combination already exists, handle accordingly
            entityManager.getTransaction().rollback();
            // You can throw an exception, return a result, or perform any other action
            return;
        }

        // Combination does not exist, persist the entity
        entityManager.persist(albumArtist);
        entityManager.getTransaction().commit();

    }
}
