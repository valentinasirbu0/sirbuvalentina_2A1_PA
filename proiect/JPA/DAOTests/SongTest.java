package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Song;
import org.example.JPA.repos.SongRepository;

import java.nio.file.Path;

public class SongTest {
    public static Song SongTest(String name, byte[] fileData, Path outputPath) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        SongRepository songRepository = new SongRepository(entityManager);
        Song song = new Song(outputPath.toString(), name, fileData);
        songRepository.create(song);
        entityManager.close();
        return song;
    }
}
