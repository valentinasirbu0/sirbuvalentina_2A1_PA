package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.*;
import org.example.JPA.repos.AlbumGenreRepository;
import org.example.JPA.repos.AlbumArtistRepository;
import org.example.JPA.repos.AlbumRepository;
import org.example.JPA.repos.ArtistRepository;
import org.example.JPA.repos.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AlbumArtistTest {
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
                Set<Genre> assignedGenres = new HashSet<>();
                for (int i = 0; i < numberOfGenres; i++) {
                    int randomIndex = new Random().nextInt(genres.size());
                    Genre randomGenre = genres.get(randomIndex);

                    assignedGenres.add(randomGenre);
                }

                // Create album-genre associations
                for (Genre genre : assignedGenres) {
                    AlbumGenre albumGenre = new AlbumGenre(album, genre);
                    albumGenreRepository.create(albumGenre);
                }

                // Retrieve all artists from the artists table
                ArtistRepository artistRepository = new ArtistRepository(entityManager);
                List<Artist> artists = artistRepository.getAll();

                // Assign random number of artists (1 to 3) to the album
                int numberOfArtists = new Random().nextInt(3) + 1; // Random number from 1 to 3
                Set<Artist> assignedArtists = new HashSet<>();
                for (int i = 0; i < numberOfArtists; i++) {
                    int randomIndex = new Random().nextInt(artists.size());
                    Artist randomArtist = artists.get(randomIndex);

                    assignedArtists.add(randomArtist);
                }

                AlbumArtistRepository albumArtistRepository = new AlbumArtistRepository(entityManager);
                // Create album-artist associations
                for (Artist artist : assignedArtists) {
                    AlbumArtist albumArtist = new AlbumArtist(album, artist);
                    albumArtistRepository.create(albumArtist);
                }
            }
        } finally {
            // Close the entityManager after all albums have been processed
            entityManager.close();
        }
    }
}
