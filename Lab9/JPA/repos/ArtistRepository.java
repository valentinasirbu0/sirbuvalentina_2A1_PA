package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.JPA.model.Artist;

import java.util.List;

public class ArtistRepository extends AbstractRepository<Artist, Integer> {

    public ArtistRepository(EntityManager entityManager) {
        super(entityManager, Artist.class);
    }

    public void create(Artist artist) {
        entityManager.getTransaction().begin();
        if (artist.getName() == null) {
            entityManager.persist(artist);
        } else {
            List<Artist> artists = findByName(artist.getName());
            if (artists.isEmpty()) {
                entityManager.persist(artist);
            } else {
                artist.setId(artists.get(0).getId());
            }
        }
        entityManager.getTransaction().commit();
    }

    public Artist findById(Integer id) {
        return entityManager.find(Artist.class, id);
    }

    public List<Artist> findByName(String namePattern) {
        TypedQuery<Artist> query = entityManager.createNamedQuery("Artist.findByName", Artist.class);
        query.setParameter("name", "%" + namePattern + "%");
        return query.getResultList();
    }
}