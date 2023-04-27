package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String args[]) {

        var artists = new ArtistDAO();
        artists.create("Pink Floyd");
        artists.create("Michael Jackson");

        var genres = new GenreDAO();
        genres.create("Rock");
        genres.create("Funk");
        genres.create("Soul");
        genres.create("Pop");

        try {
            Database.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        var albums = new AlbumDAO();
        albums.create(1979, "The Wall", "Pink Floyd", "Rock");
        albums.create(1982, "Thriller", "Michael Jackson", "Funk","Soul","Pop");

        try {
            Database.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Database.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

