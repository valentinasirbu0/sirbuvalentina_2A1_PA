package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Album;
import org.example.JPA.model.Artist;
import org.example.JPA.repos.AlbumRepository;

import java.util.List;

public class AlbumDAOTest {
    public static void AlbumDAOTest() {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumRepository albumRepository = new AlbumRepository(entityManager);

        Album album = new Album(2002, "ALBUM", ArtistsDAOTest.ArtistTest("Sanda"));
        albumRepository.create(album);

        Album foundAlbum = albumRepository.findById(1);
        System.out.println("Album found by ID: " + foundAlbum.getTitle());

        List<Album> albumsByName = albumRepository.findByTitle("ALBUM");
        System.out.println("Albums found by title pattern: " + albumsByName.get(0).getId());

        entityManager.close();
    }

    public static Album AlbumTest(Integer year, String title, Artist artist) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumRepository albumRepository = new AlbumRepository(entityManager);
        Album album = new Album(year, title, artist);
        albumRepository.create(album);
        entityManager.close();
        return album;
    }
}
