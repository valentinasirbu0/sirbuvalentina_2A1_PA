package test;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.AlbumDAO;
import org.example.ArtistDAO;
import org.example.Database;
import org.example.GenreDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AlbumDAOTest {
    private AlbumDAO albumDAO;

    @Before
    public void setUp() throws SQLException {
        var artists = new ArtistDAO();
        artists.create("Pink Floyd");

        var genres = new GenreDAO();
        genres.create("Rock");

        albumDAO = new AlbumDAO();
    }

    @After
    public void tearDown() throws SQLException {
        Database.getConnection().close();
    }

    @Test
    public void testCreate1() throws SQLException {
        String artist = "Pink Floyd";
        String genre1 = "Rock";
        albumDAO.create(1979, "The Wall", artist, genre1);

        String sql = "SELECT * FROM albums WHERE title = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setString(1, "The Wall");
            try (ResultSet resultSet = statement.executeQuery()) {
                int count = 0;
                while (resultSet.next()) {
                    assertEquals(1979, resultSet.getInt("year"));
                }
                assertEquals(0, count); // The album should have 1 genres.
            }
        }
    }

    @Test
    public void testCreate2() throws SQLException {
        String artist = "Pink Floyd";
        String genre1 = "Rock";
        String genre2 = "Progressive Rock";
        String genre3 = "Psychedelic Rock";
        albumDAO.create(1979, "The Wall", artist, genre1, genre2, genre3);

        String sql = "SELECT * FROM albums WHERE title = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
            statement.setString(1, "The Wall");
            try (ResultSet resultSet = statement.executeQuery()) {
                int count = 0;
                while (resultSet.next()) {
                    assertEquals(1979, resultSet.getInt("year"));
                    assertEquals(artist, resultSet.getString("artist"));
                    String genre = resultSet.getString("genre");
                    if (genre.equals(genre1) || genre.equals(genre2) || genre.equals(genre3)) {
                        count++;
                    }
                }
                assertEquals(3, count); // The album should have 3 genres.
            }
        }
    }
}