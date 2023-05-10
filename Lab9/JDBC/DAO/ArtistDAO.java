package org.example.JDBC.DAO;

import org.example.JDBC.JdbcDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ArtistDAO {
    private static Connection connection = null;

    public ArtistDAO() {
        connection = JdbcDAOFactory.getConnection();
    }

    public void create(String name) {
        if (getArtistId(name) == null) {
            addInDataBase(name);
        }
    }

    private void addInDataBase(String name) {
        String sql = "INSERT INTO artists (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer getArtistId(String name) {
        String sql = "SELECT id FROM artists WHERE name = ?";
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}