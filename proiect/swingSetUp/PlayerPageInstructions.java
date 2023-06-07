package org.example.swingSetUp;

import jakarta.persistence.EntityManager;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.example.API.GoogleTranslateAPI;
import org.example.API.LyricsExtractor;
import org.example.JPA.DAOTests.SongTest;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Album;
import org.example.JPA.model.Song;
import org.example.JPA.repos.AlbumRepository;
import org.example.JPA.repos.AlbumSongRepository;
import org.example.JPA.repos.SongRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.example.API.GoogleTranslateAPI.filePath;
import static org.example.API.GoogleTranslateAPI.parseLanguages;
import static org.example.API.ShazamAPI.identifySong;
import static org.example.swingSetUp.MusicPlayer.*;

public class PlayerPageInstructions {
    public static void loadLanguageOptions() {
        // Read the JSON file
        String jsonContent = null;
        try {
            jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> languages = parseLanguages(jsonContent);
        for (String s : languages) {
            languageComboBox.addItem(s);
        }
    }


    public static void loadMidiFileNames(String albumName) {
        try {
            midiComboBox.removeAllItems();
            if (albumName != null) {
                EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
                AlbumRepository albumRepository = new AlbumRepository(entityManager);

                // Find the album based on the albumName
                List<Album> albums = albumRepository.findByTitle(albumName);
                if (!albums.isEmpty()) {
                    Album album = albums.get(0);

                    // Retrieve the song names associated with the album
                    AlbumSongRepository albumSongRepository = new AlbumSongRepository(entityManager);
                    List<String> songNames = albumSongRepository.findSongNamesByAlbum(album);

                    // Add the song names to the midiComboBox
                    for (String songName : songNames) {
                        midiComboBox.addItem(songName);
                    }
                } else {
                    System.out.println("No album found with the title: " + albumName);
                }

                entityManager.close();
            } else {
                System.out.println("Album name is null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToFavorites(Connection connection, String song) {
        Long songId = SongTest.findSongIdByName(song);
        try {
            String sql = "INSERT INTO users_favourites (user_id, song_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, LoginPage.userID);
            statement.setInt(2, Math.toIntExact(songId));
            statement.executeUpdate();
            System.out.println("Added to favorites: Song ID " + songId + " for User ID " + LoginPage.userID);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add song to favorites.");
        }
    }

    public static void playMidiFile(String midiName) {
        try {
            // Load the MP3 file from the database and play it
            String albumName = (String) albumComboBox.getSelectedItem();
            String languageName = (String) languageComboBox.getSelectedItem();
            EntityManager entityManager = JpaDAOFactory.getEntityManagerFactory().createEntityManager();
            SongRepository songRepository = new SongRepository(entityManager);

            // Find the song based on the midiName
            List<Song> songs = songRepository.findByName(midiName);
            if (!songs.isEmpty()) {
                Song song = songs.get(0);

                // Play the MP3 data
                byte[] mp3Data = song.getFileData();

                ///////////////////////////////////////////////////////////////////////////////////////////////////////
                // Identify the song
                identifySong(song.getPath());
                String lyrics = LyricsExtractor.lyrics;
                String translated = GoogleTranslateAPI.translate(lyrics, languageName);

                Thread lyricsThread = new Thread(() -> {
                    // Display the lyrics in the lyricsTextArea
                    //System.out.println(lyrics);
                    //lyricsTextArea.setText(song.getName());
                    if (translated != null) {
                        lyricsTextArea.setText(translated);
                    } else {
                        lyricsTextArea.setText(lyrics);
                    }

                });
                lyricsThread.start();

                ///////////////////////////////////////////////////////////////////////////////////////////////////////

                // Create an input stream from the MP3 data
                ByteArrayInputStream bis = new ByteArrayInputStream(mp3Data);

                // Create a Player instance
                player = new Player(bis);

                // Start playback in a separate thread
                Thread playerThread = new Thread(() -> {
                    try {
                        player.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                });
                playerThread.start();
            } else {
                System.out.println("No song found with the name: " + midiName);
            }

            entityManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMidiFile() {
        try {
            if (player != null) {
                player.close();
                player = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFavouritesList() {
        try {
            String sql = "SELECT songs.name FROM users_favourites " +
                    "INNER JOIN songs ON users_favourites.song_id = songs.id " +
                    "WHERE users_favourites.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, LoginPage.userID);
            ResultSet resultSet = statement.executeQuery();

            // Clear existing items in the favorites combo box
            favouritesComboBox.removeAllItems();

            // Add the songs to the favorites combo box
            while (resultSet.next()) {
                String songTitle = resultSet.getString("name");
                favouritesComboBox.addItem(songTitle);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
