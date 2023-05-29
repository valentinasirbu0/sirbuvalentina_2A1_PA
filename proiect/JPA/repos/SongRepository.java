package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.example.JPA.model.Song;

import java.util.List;


@Transactional
public class SongRepository extends AbstractRepository<Song, Integer> {
    public SongRepository(EntityManager entityManager) {
        super(entityManager, Song.class);
    }

    public void create(Song song) {
        entityManager.getTransaction().begin();
        if (song.getName() == null) {
            entityManager.persist(song);
        } else {
            List<Song> songs = findByName(song.getName());
            if (songs.isEmpty()) {
                entityManager.persist(song);
            } else {
                song.setId(songs.get(0).getId());
            }
        }
        entityManager.getTransaction().commit();
    }

    public List<Song> findByName(String namePattern) {
        TypedQuery<Song> query = entityManager.createNamedQuery("Song.findByName", Song.class);
        query.setParameter("name", "%" + namePattern + "%");
        return query.getResultList();
    }

    public List<Song> getAll() {
        TypedQuery<Song> query = entityManager.createNamedQuery("Song.findAll", Song.class);
        return query.getResultList();
    }
}
