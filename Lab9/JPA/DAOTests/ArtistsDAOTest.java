package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.repos.ArtistRepository;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Artist;

import java.util.List;

import static org.example.JPA.DataSets.CSVData.removeSpecialChars;

public class ArtistsDAOTest {
    public static void ArtistDAOTest() {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        ArtistRepository artistRepository = new ArtistRepository(entityManager);

        // Create an artist
        Artist a = new Artist("Valentina");
        artistRepository.create(a);

        // Find the artist by ID
        Artist foundArtist = artistRepository.findById(2);
        System.out.println("Artist found by ID: " + foundArtist.getName());

        // Find the artists by name pattern
        Artist artist = artistRepository.findByName("Valentina").get(0);
        System.out.println("Artists found by name pattern:");
        System.out.println(artist.getId() + " - " + artist.getName());

        entityManager.close();
    }

    public static Artist ArtistTest(String name) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        ArtistRepository artistRepository = new ArtistRepository(entityManager);

        // Create an artist
        Artist a = new Artist(removeSpecialChars(name));
        artistRepository.create(a);
        entityManager.close();
        return a;
    }
}
