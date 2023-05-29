package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Song;
import org.example.JPA.repos.SongRepository;

public class SongTest {
    public static Song SongTest(String name, byte[] fileData) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        SongRepository songRepository = new SongRepository(entityManager);
        Song song = new Song(name, fileData);
        songRepository.create(song);
        entityManager.close();
        return song;
    }
}
