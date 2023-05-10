package org.example.JDBC.DAO;

import org.example.JDBC.JdbcDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumDAO {
    private static Connection connection = null;

    public AlbumDAO() {
        connection = JdbcDAOFactory.getConnection();
    }

    public void create(int year, String title, String artist, String genre) {
        if (getAlbumId(year, title) == null) {
            addInAlbums(year, title, artist);
            addInAlbumsGenre(year, title, genre);
        }
    }

    private void addInAlbums(java.lang.Integer year, String title, String artist) {
        String sql = "INSERT INTO albums (year, title, artist_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, year);
            statement.setString(2, title);
            statement.setInt(3, ArtistDAO.getArtistId(artist));
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addInAlbumsGenre(java.lang.Integer year, String title, String genre) {
        String[] words = genre.split("[, /\"]+");
        for (String word : words) {
            String sql = "INSERT INTO album_genre (album_id, genre_id) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, getAlbumId(year, title));
                if (GenreDAO.getGenreId(word) == null) {
                    var genres = new GenreDAO();
                    genres.create(word);
                }
                statement.setInt(2, GenreDAO.getGenreId(word));
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private java.lang.Integer getAlbumId(java.lang.Integer year, String title) {
        String sql = "SELECT id FROM albums WHERE year = CAST(? AS INTEGER) AND title = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, year);
            statement.setString(2, title);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
