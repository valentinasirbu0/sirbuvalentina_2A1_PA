package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Song;
import org.example.JPA.model.User;
import org.example.JPA.model.UserFavourite;
import org.example.JPA.repos.UsersFavouriteRepository;

import java.util.ArrayList;
import java.util.List;

public class UsersFavouritesTest {
    public static UserFavourite UsersFavourite(User user, Song song) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        UsersFavouriteRepository usersFavouritesRepository = new UsersFavouriteRepository(entityManager);

        // Create an artist
        UserFavourite a = new UserFavourite(user, song);
        usersFavouritesRepository.create(a);
        entityManager.close();
        return a;
    }

    public static List<Song> findAllFavourites(User user) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        UsersFavouriteRepository usersFavouritesRepository = new UsersFavouriteRepository(entityManager);

        List<UserFavourite> result = usersFavouritesRepository.getAllFavourites(user);
        List<Song> songs = new ArrayList<>();
        for (UserFavourite u : result) {
            songs.add(u.getSong());
        }
        entityManager.close();
        return songs;
    }
}
