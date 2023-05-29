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
            List<Album> existingAlbums = findByTitle(album.getTitle());
            if (existingAlbums.isEmpty()) {
                entityManager.persist(album);
            } else {
                Album existingAlbum = existingAlbums.get(0);
                entityManager.merge(existingAlbum);
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

    public List<Album> getAll() {
        TypedQuery<Album> query = entityManager.createNamedQuery("Album.findAll", Album.class);
        return query.getResultList();
    }

}
