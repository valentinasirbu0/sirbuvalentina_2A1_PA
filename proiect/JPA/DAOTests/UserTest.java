package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.User;
import org.example.JPA.repos.UserRepository;

public class UserTest {
    public static User UserTest(String name, String password) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        UserRepository userRepository = new UserRepository(entityManager);

        // Create an artist
        User a = new User(name, password);
        userRepository.create(a);
        entityManager.close();
        return a;
    }

    public static String findUserPassword(String name) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        UserRepository userRepository = new UserRepository(entityManager);
        String result = userRepository.findByName(name).get(0).getPassword();
        entityManager.close();
        return result;
    }

    public static User findUser(String name) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        UserRepository userRepository = new UserRepository(entityManager);
        User result = userRepository.findByName(name).get(0);
        entityManager.close();
        return result;
    }

    public static boolean checkUsername(String name) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        UserRepository userRepository = new UserRepository(entityManager);
        boolean result = true;
        if (userRepository.findByName(name).isEmpty()) {
            result = false;
        }
        entityManager.close();
        return result;
    }
}
