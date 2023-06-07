package org.example.JPA.DAOTests;

import jakarta.persistence.EntityManager;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Album;
import org.example.JPA.model.AlbumSong;
import org.example.JPA.model.Song;
import org.example.JPA.repos.AlbumRepository;
import org.example.JPA.repos.AlbumSongRepository;
import org.example.JPA.repos.SongRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlbumSongTest {
    public static void AlbumSongTest() {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumSongRepository albumSongRepository = new AlbumSongRepository(entityManager);

        try {
            // Retrieve all albums from the albums table
            SongRepository songRepository = new SongRepository(entityManager);
            List<Song> songs = songRepository.getAll();

            // Iterate over the songs and assign a random number of genres (1 to 5)
            for (Song song : songs) {
                AlbumRepository albumRepository = new AlbumRepository(entityManager);
                List<Album> albums = albumRepository.getAll();
                if (albums.isEmpty()) {
                    throw new IllegalStateException("No albums found in the database.");
                }

                int numberOfAlbums = new Random().nextInt(5) + 1; // Random number from 1 to 5

                // Select random genres to assign
                for (int i = 0; i < numberOfAlbums; i++) {
                    int randomIndex = new Random().nextInt(albums.size());
                    Album randomAlbum = albums.get(randomIndex);

                    AlbumSong albumSong = new AlbumSong(randomAlbum, song);
                    albumSongRepository.create(albumSong);
                }
            }
        } finally {
            // Close the entityManager after all albums have been processed
            entityManager.close();
        }
    }

    public static List<Album> getAlbumsForSong(Song song) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumSongRepository albumSongRepository = new AlbumSongRepository(entityManager);
        List<AlbumSong> result = albumSongRepository.findAlbumsForSong(song);
        List<Album> albums = new ArrayList<>();
        for (AlbumSong a : result) {
            albums.add(a.getAlbum());
        }
        entityManager.close();
        return albums;
    }

    public static List<Song> getSongsInAlbum(Album album) {
        EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
        AlbumSongRepository albumSongRepository = new AlbumSongRepository(entityManager);
        List<AlbumSong> result = albumSongRepository.findSongsInAlbum(album);
        List<Song> songs = new ArrayList<>();
        for (AlbumSong a : result) {
            songs.add(a.getSong());
        }
        entityManager.close();
        return songs;
    }
}
