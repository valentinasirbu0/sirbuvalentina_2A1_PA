package org.example._9.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example._9.model.Artist;

import java.util.List;

public class ArtistRepository {
    private EntityManager entityManager;

    public ArtistRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Artist artist) {
        entityManager.getTransaction().begin();
        if (artist.getId() == null) {
            entityManager.persist(artist);
        } else {
            entityManager.merge(artist);
        }
        entityManager.getTransaction().commit();
    }

    public Artist findById(int id) {
        return entityManager.find(Artist.class, id);
    }

    public List<Artist> findByName(String namePattern) {
        TypedQuery<Artist> query = entityManager.createNamedQuery("Artist.findByName", Artist.class);
        query.setParameter("name", "%" + namePattern + "%");
        return query.getResultList();
    }
}