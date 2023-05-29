package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Album;
import org.example.JPA.model.Artist;
import org.example.JPA.repos.AlbumRepository;
import org.example.JPA.repos.ArtistRepository;

import java.util.List;
import java.util.Random;

public class AlbumDAOTest {
    public static Album AlbumTest(String year, String title, Artist artist) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumRepository albumRepository = new AlbumRepository(entityManager);

        Album album = new Album(year, title, artist);
        albumRepository.create(album);
        entityManager.close();
        return album;
    }

    public static List<Album> getAllAlbums() {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumRepository albumRepository = new AlbumRepository(entityManager);
        List<Album> list = albumRepository.getAll();
        entityManager.close();
        return list;
    }

    public static Album AlbumTest(String year, String title) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumRepository albumRepository = new AlbumRepository(entityManager);

        // Retrieve a random album ID from the existing albums
        ArtistRepository artistRepository = new ArtistRepository(entityManager);
        List<Artist> artists = artistRepository.getAll();
        if (artists.isEmpty()) {
            throw new IllegalStateException("No artists found in the database.");
        }
        int randomIndex = new Random().nextInt(artists.size());
        Artist randomArtist = artists.get(randomIndex);

        Album album = new Album(year, title, randomArtist);
        albumRepository.create(album);
        entityManager.close();
        return album;
    }
}
