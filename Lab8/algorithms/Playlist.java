package org.example.algorithms;

import org.example.database.Database;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.sql.*;

public class Playlist {
    private static Connection connection = null;

    public Playlist(String name) {
        try {
            connection = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!existsPlaylist(name)) {
            createPlaylist(name);
            addPlaylist(name);
        }
    }

    private boolean existsPlaylist(String name) {
        String sql = "SELECT COUNT(*) FROM information_schema.tables " +
                "WHERE table_schema = 'public' AND table_name = ?";
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private void addPlaylist(String name) {
        String sql = "INSERT INTO playlists (name, created_at) VALUES (?, CURRENT_TIMESTAMP)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPlaylist(String name) {
        String sql =
                "CREATE TABLE " + name + " (\n" +
                        "    id SERIAL,\n" +
                        "    album_id INTEGER PRIMARY KEY NOT NULL REFERENCES albums(id),\n" +
                        "    UNIQUE(id),\n" +
                        "    UNIQUE(album_id)\n" +
                        ");";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Graph<Integer, DefaultEdge> createGraph() {
        Graph<Integer, DefaultEdge> originalGraph = new SimpleGraph<>(DefaultEdge.class);

        String sql = "SELECT * FROM albums";
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int year1 = rs.getInt("year");
                String artist1 = rs.getString("artist_id");
                int id1 = rs.getInt("id");
                while (rs.next()) {
                    int year2 = rs.getInt("year");
                    String artist2 = rs.getString("artist_id");
                    int id2 = rs.getInt("id");
                    if (year1 == year2 || artist1.equals(artist2)) {
                        originalGraph.addVertex(id1);
                        originalGraph.addVertex(id2);
                        originalGraph.addEdge(id1, id2);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql = "SELECT * FROM album_genre";
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id1 = rs.getInt("album_id");
                String genre1 = rs.getString("genre_id");
                while (rs.next()) {
                    String genre2 = rs.getString("genre_id");
                    int id2 = rs.getInt("album_id");
                    if (genre1 == genre2) {
                        originalGraph.addVertex(id1);
                        originalGraph.addVertex(id2);
                        originalGraph.addEdge(id1, id2);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Graph<Integer, DefaultEdge> transposedGraph = new SimpleGraph<>(DefaultEdge.class);
        for (DefaultEdge edge : originalGraph.edgeSet()) {
            Integer sourceVertex = originalGraph.getEdgeSource(edge);
            Integer targetVertex = originalGraph.getEdgeTarget(edge);
            transposedGraph.addVertex(sourceVertex);
            transposedGraph.addVertex(targetVertex);
            transposedGraph.addEdge(targetVertex, sourceVertex);
        }
        return transposedGraph;
    }

    public static void populatePlaylist(String name, String method) {
        if (method == "CF") {
            cliqueFinder(name);
        } else if (method == "Clustering") {
            clustering(name);
        }
    }

    private static void clustering(String name) {
        Clustering cluster = new Clustering(createGraph());
        String insertSql = "INSERT INTO " + name + " (album_id) VALUES (?)";
        String selectSql = "SELECT album_id FROM " + name + " WHERE album_id = ?";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            for (Integer value : cluster.result.keySet()) {
                // Check if the album ID already exists in the database
                selectStatement.setInt(1, value);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    // Album ID already exists, skip insertion
                    //System.out.println("Album ID " + value + " already exists in the database");
                    continue;
                }
                // Album ID does not exist, insert it into the database
                insertStatement.setInt(1, value);
                insertStatement.executeUpdate();
                connection.commit();
                //System.out.println("Inserted album ID " + value + " into the database");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void cliqueFinder(String name) {
        CliqueFinder clique = new CliqueFinder(createGraph());
        String insertSql = "INSERT INTO " + name + " (album_id) VALUES (?)";
        String selectSql = "SELECT album_id FROM " + name + " WHERE album_id = ?";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            for (Integer value : clique.returnCliques()) {
                // Check if the album ID already exists in the database
                selectStatement.setInt(1, value);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    // Album ID already exists, skip insertion
                    //System.out.println("Album ID " + value + " already exists in the database");
                    continue;
                }
                // Album ID does not exist, insert it into the database
                insertStatement.setInt(1, value);
                insertStatement.executeUpdate();
                connection.commit();
                //System.out.println("Inserted album ID " + value + " into the database");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
