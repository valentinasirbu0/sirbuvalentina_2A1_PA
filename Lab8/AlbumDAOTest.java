package test;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.DAO.AlbumDAO;
import org.example.DAO.ArtistDAO;
import org.example.database.Database;
import org.example.DAO.GenreDAO;
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

}