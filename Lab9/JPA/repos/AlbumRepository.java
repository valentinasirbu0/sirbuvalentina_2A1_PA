package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.JPA.model.Album;

import java.util.List;

public class AlbumRepository extends AbstractRepository<Album, Integer> {
    public AlbumRepository(EntityManager entityManager) {
        super(entityManager, Album.class);
    }

    public void create(Album album) {
        entityManager.getTransaction().begin();
        if (album.getId() == null) {
            entityManager.persist(album);
        }else {
            List<Album> albums = findByTitle(album.getTitle());
            if (albums.isEmpty()) {
                entityManager.persist(album);
            } else {
                album.setId(albums.get(0).getId());
            }
        }
        entityManager.getTransaction().commit();
    }

    public Album findById(int id) {
        return entityManager.find(Album.class, id);
    }

    public List<Album> findByTitle(String titlePattern) {
        TypedQuery<Album> query = entityManager.createNamedQuery("Album.findByTitle", Album.class);
        query.setParameter("title", "%" + titlePattern + "%");
        return query.getResultList();
    }
}
