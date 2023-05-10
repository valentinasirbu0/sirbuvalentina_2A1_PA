package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Genre;
import org.example.JPA.repos.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.example.JPA.DataSets.CSVData.removeSpecialChars;

public class GenreDAOTest {
    public static void GenreDAOTest() {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        GenreRepository genreRepository = new GenreRepository(entityManager);

        Genre a = new Genre("Barbie");
        genreRepository.create(a);

        Genre foundGenre = genreRepository.findById(1);
        System.out.println("Genres found by ID: " + foundGenre.getName());

        List<Genre> genresByName = genreRepository.findByName("Barbie");
        System.out.println("Genres found by name pattern:");
        for (Genre genre : genresByName) {
            System.out.println(genre.getId() + " - " + genre.getName());
        }
        entityManager.close();
    }

    public static List<Genre> GenreTest(String name) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        GenreRepository genreRepository = new GenreRepository(entityManager);
        List<Genre> genres = new ArrayList<>();

        String[] genresArray = name.split("[,/]");
        for (String genre : genresArray) {
            Genre a = new Genre(removeSpecialChars(genre));
            genreRepository.create(a);
            genres.add(a);
        }
        entityManager.close();
        return genres;
    }
}
