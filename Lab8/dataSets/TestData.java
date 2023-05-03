package org.example.dataSets;

import org.example.DAO.AlbumDAO;
import org.example.DAO.ArtistDAO;
import org.example.DAO.GenreDAO;

public class TestData {
    public TestData() {
        var artists = new ArtistDAO();
        artists.create("Pink Floyd");
        artists.create("Michael Jackson");

        var genres = new GenreDAO();
        genres.create("Rock");
        genres.create("Funk");
        genres.create("Soul");
        genres.create("Pop");


        var albums = new AlbumDAO();
        albums.create(1979, "The Wall", "Pink Floyd", "Rock");
        albums.create(1982, "Thriller", "Michael Jackson", "Funk,Soul,Pop");



    }
}
