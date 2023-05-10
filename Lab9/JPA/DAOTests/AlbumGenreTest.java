package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Album;
import org.example.JPA.model.AlbumGenre;
import org.example.JPA.model.Genre;
import org.example.JPA.repos.AlbumGenreRepository;

public class AlbumGenreTest {
    public static void AlbumGenreTest(Album album, Genre genre) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumGenreRepository albumGenreRepository = new AlbumGenreRepository(entityManager);

        AlbumGenre albumGenre = new AlbumGenre(album, genre);
        albumGenreRepository.create(albumGenre);

        entityManager.close();
    }
}
