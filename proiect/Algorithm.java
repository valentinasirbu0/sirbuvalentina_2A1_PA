package org.example;

import org.example.JPA.DAOTests.AlbumSongTest;
import org.example.JPA.DAOTests.UsersFavouritesTest;
import org.example.JPA.model.Album;
import org.example.JPA.model.Song;
import org.example.swingSetUp.LoginPage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    public static List<Song> findSuggestions(Connection connection) {
        List<Song> recommendedSongs = new ArrayList<>();

        List<Song> songs = UsersFavouritesTest.findAllFavourites(LoginPage.user);
        List<Album> albums = new ArrayList<>();
        List<Song> songs2 = new ArrayList<>();
        for (Song s : songs) {
            albums = AlbumSongTest.getAlbumsForSong(s);
        }
        for (Album a : albums) {
            songs2 = AlbumSongTest.getSongsInAlbum(a);
        }
        for (Song s2 : songs2) {
            recommendedSongs.add(s2);
        }

        return recommendedSongs;
    }

}



