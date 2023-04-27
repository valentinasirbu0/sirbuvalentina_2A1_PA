package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlbumDAO {
    private final Connection connection;

    public AlbumDAO() {
        connection = Database.getConnection();
    }

    public void create(int year, String title, String artist, String genre) {
        String sql = "INSERT INTO albums (year, title, artist_id, genre_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, year);
            statement.setString(2, title);
            statement.setInt(3, ArtistDAO.getArtistId(artist));
            statement.setInt(4, GenreDAO.getGenreId(genre));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(int year, String title, String artist, String genre1, String genre2, String genre3) {
        create(year,title,artist,genre1);
        create(year,title,artist,genre2);
        create(year,title,artist,genre3);
    }

}
