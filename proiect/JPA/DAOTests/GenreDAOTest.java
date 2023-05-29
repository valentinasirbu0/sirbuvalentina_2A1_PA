package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Genre;
import org.example.JPA.repos.GenreRepository;

import java.util.List;

public class GenreDAOTest {
    public static Genre GenreTest(String name) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        GenreRepository genreRepository = new GenreRepository(entityManager);

        Genre a = new Genre(name);
        genreRepository.create(a);

        entityManager.close();
        return a;
    }

    public static Genre findGenreByName(String name) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        GenreRepository genreRepository = new GenreRepository(entityManager);
        List<Genre> genres = genreRepository.findByName(name);
        entityManager.close();

        if (!genres.isEmpty()) {
            return genres.get(0); // Assuming you want to return the first matching genre
        } else {
            return null; // Return null if no genre is found
        }
    }

}
