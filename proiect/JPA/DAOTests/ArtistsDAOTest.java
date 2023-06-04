package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Artist;
import org.example.JPA.repos.ArtistRepository;

import java.util.List;

public class ArtistsDAOTest {
    public static Artist ArtistTest(String name) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        ArtistRepository artistRepository = new ArtistRepository(entityManager);

        // Create an artist
        Artist a = new Artist(name);
        artistRepository.create(a);
        entityManager.close();
        return a;
    }

    public static Artist findArtistByName(String name) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        ArtistRepository artistRepository = new ArtistRepository(entityManager);
        // Find the artists by name pattern
        List<Artist> artists = artistRepository.findByName(name);

        if (artists.isEmpty()) {
            // No artists found with the given name, handle the error case
            ArtistTest(name);
            artists = artistRepository.findByName(name); // Retry finding the artist after handling the error
            if (artists.isEmpty()) {
                // No artist found even after retry, return null or handle the error case as needed
                entityManager.close();
                return null;
            }
        }

        // At least one artist found, return the first artist from the list
        Artist artist = artists.get(0);
        entityManager.close();
        return artist;
    }
}
