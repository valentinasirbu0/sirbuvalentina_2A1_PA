package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Album;
import org.example.JPA.model.AlbumGenre;
import org.example.JPA.model.Genre;
import org.example.JPA.repos.AlbumGenreRepository;
import org.example.JPA.repos.AlbumRepository;
import org.example.JPA.repos.GenreRepository;

import java.util.List;
import java.util.Random;

public class AlbumGenreTest {
    public static void AlbumGenreTest() {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumGenreRepository albumGenreRepository = new AlbumGenreRepository(entityManager);

        try {
            // Retrieve all albums from the albums table
            AlbumRepository albumRepository = new AlbumRepository(entityManager);
            List<Album> albums = albumRepository.getAll();

            // Iterate over the albums and assign a random number of genres (1 to 5)
            for (Album album : albums) {
                GenreRepository genreRepository = new GenreRepository(entityManager);
                List<Genre> genres = genreRepository.getAll();
                if (genres.isEmpty()) {
                    throw new IllegalStateException("No genres found in the database.");
                }

                int numberOfGenres = new Random().nextInt(5) + 1; // Random number from 1 to 5

                // Select random genres to assign
                for (int i = 0; i < numberOfGenres; i++) {
                    int randomIndex = new Random().nextInt(genres.size());
                    Genre randomGenre = genres.get(randomIndex);

                    AlbumGenre albumGenre = new AlbumGenre(album, randomGenre);
                    albumGenreRepository.create(albumGenre);
                }
            }
        } finally {
            // Close the entityManager after all albums have been processed
            entityManager.close();
        }
    }
}
