package org.example.JPA.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.example.JPA.model.Album;
import org.example.JPA.model.AlbumSong;
import org.example.JPA.model.Song;

import java.util.List;

public class AlbumSongRepository extends AbstractRepository<AlbumSong, Long> {
    public AlbumSongRepository(EntityManager entityManager) {
        super(entityManager, AlbumSong.class);
    }

    public void create(AlbumSong albumSong) {
        entityManager.getTransaction().begin();

        // Check if the combination already exists
        Album album = albumSong.getAlbum();
        Song song = albumSong.getSong();
        if (album == null || song == null) {
            entityManager.getTransaction().rollback();
            // Handle the case where either album or genre is null
            // You can throw an exception, return a result, or perform any other action
            return;
        }
        AlbumSong compositeKey = new AlbumSong(album, song);
        AlbumSong existingAlbumSong = entityManager.find(AlbumSong.class, compositeKey);
        if (existingAlbumSong != null) {
            // Combination already exists, handle accordingly
            entityManager.getTransaction().rollback();
            // You can throw an exception, return a result, or perform any other action
            return;
        }

        // Combination does not exist, persist the entity
        entityManager.persist(albumSong);
        entityManager.getTransaction().commit();

    }

    public List<String> findSongNamesByAlbum(Album album) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);

        Root<AlbumSong> albumSongRoot = query.from(AlbumSong.class);
        Join<AlbumSong, Album> albumJoin = albumSongRoot.join("album");
        Join<AlbumSong, Song> songJoin = albumSongRoot.join("song");

        query.select(songJoin.get("name"))
                .where(criteriaBuilder.equal(albumJoin.get("id"), album.getId()));

        return entityManager.createQuery(query).getResultList();
    }

    public List<AlbumSong> findAlbumsForSong(Song song) {
        TypedQuery<AlbumSong> query = entityManager.createNamedQuery("AlbumSong.findAlbumsforSong", AlbumSong.class);
        query.setParameter("songId", song.getId());
        return query.getResultList();
    }

    public List<AlbumSong> findSongsInAlbum(Album album) {
        TypedQuery<AlbumSong> query = entityManager.createNamedQuery("AlbumSong.findSongsInAlbum", AlbumSong.class);
        query.setParameter("albumId", album.getId());
        return query.getResultList();
    }
}
