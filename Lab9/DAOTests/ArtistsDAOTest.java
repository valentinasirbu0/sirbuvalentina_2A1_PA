package org.example._9.DAOTests;

import jakarta.persistence.EntityManager;
import org.example._9.repos.ArtistRepository;
import org.example._9.PersistenceManager;
import org.example._9.model.Artist;

import java.util.List;

public class ArtistsDAOTest {
    public static void ArtistDAOTest() {
        EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager();
        ArtistRepository artistRepository = new ArtistRepository(entityManager);
/*
        // Create an artist
        Artist a = new Artist();
        a.setId(123535);
        a.setName("vdrghgnjghnfr");
        artistRepository.create(a);
*/
        // Find the artist by ID
        Artist foundArtist = artistRepository.findById(1488);
        System.out.println("Artist found by ID: " + foundArtist);

        // Find the artists by name pattern
        List<Artist> artistsByName = artistRepository.findByName("valea");
        System.out.println("Artists found by name pattern: " + artistsByName);

        entityManager.close();
    }
}
