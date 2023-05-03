package org.example.database;

import org.example.dataSets.CSVData;
import org.example.dataSets.MusicFiles;
import org.example.algorithms.Playlist;

public class DatabaseSetUp {
    public DatabaseSetUp() {
        //TestData test = new TestData();
        CSVData data = new CSVData();
        MusicFiles files = new MusicFiles();

        Playlist p1 = new Playlist("p11");
        Playlist.populatePlaylist("p11","CF");

        Playlist p2 = new Playlist("p22");
        Playlist.populatePlaylist("p22","Clustering");


        //ArtistsDAOTest.ArtistDAOTest();
        //GenreDAOTest.GenreDAOTest();
        //PersistenceManager.closeEntityManagerFactory();
    }
}
