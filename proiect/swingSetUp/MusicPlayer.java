package org.example.swingSetUp;

import jakarta.persistence.EntityManager;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.example.JPA.JpaDAOFactory;
import org.example.JPA.model.Album;
import org.example.JPA.model.Song;
import org.example.JPA.repos.AlbumRepository;
import org.example.JPA.repos.AlbumSongRepository;
import org.example.JPA.repos.SongRepository;
import org.example.LyricsExtractor;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.example.ShazamAPI.identifySong;

public class MusicPlayer extends JFrame implements ActionListener {

    private JComboBox<String> albumComboBox;
    private JComboBox<String> midiComboBox;
    private JButton playButton;
    private JButton stopButton;
    private Sequencer sequencer;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Player player;
    private JTextArea lyricsTextArea;


    public MusicPlayer() {
        super("Music Player");

        try {
            // Connect to the database
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/lab8java", "postgres", "valea");
            statement = connection.createStatement();

            // Create GUI components
            albumComboBox = new JComboBox<>();
            albumComboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Load the MIDI file names for the selected album
                    String albumName = (String) albumComboBox.getSelectedItem();
                    loadMidiFileNames(albumName);
                }
            });

            midiComboBox = new JComboBox<>();
            albumComboBox.setPreferredSize(new Dimension(200, 25));
            midiComboBox.setPreferredSize(new Dimension(200, 25));

            playButton = new JButton("Play");
            playButton.addActionListener(this);

            stopButton = new JButton("Stop");
            stopButton.addActionListener(this);

            JPanel controlPanel = new JPanel(new GridLayout(2, 2));
            controlPanel.add(new JLabel("Album:"));
            controlPanel.add(albumComboBox);
            controlPanel.add(new JLabel("MP3 File:"));
            controlPanel.add(midiComboBox);

            lyricsTextArea = new JTextArea();
            lyricsTextArea.setEditable(false);
            JScrollPane lyricsScrollPane = new JScrollPane(lyricsTextArea);

            // Set the preferred size of the lyricsScrollPane
            lyricsScrollPane.setPreferredSize(new Dimension(400, 200));

            // Create a new JPanel for the lyricsScrollPane
            JPanel lyricsPanel = new JPanel(new BorderLayout());
            lyricsPanel.add(lyricsScrollPane, BorderLayout.CENTER);
            lyricsPanel.add(controlPanel, BorderLayout.NORTH);

            // Create a new JPanel for the buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(playButton);
            buttonPanel.add(stopButton);

            // Add components to JFrame
            getContentPane().add(lyricsPanel, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);

            // Load the album names into the album combo box
            resultSet = statement.executeQuery("SELECT DISTINCT id, title FROM albums ORDER BY id ASC");
            while (resultSet.next()) {
                albumComboBox.addItem(resultSet.getString("title"));
            }

            // Load the MIDI file names for the first album
            String albumName = (String) albumComboBox.getSelectedItem();
            loadMidiFileNames(albumName);

            // Initialize the sequencer
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadMidiFileNames(String albumName) {
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


    private void playMidiFile(String midiName) {
        try {
            // Load the MP3 file from the database and play it
            String albumName = (String) albumComboBox.getSelectedItem();
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

                Thread lyricsThread = new Thread(() -> {
                    // Display the lyrics in the lyricsTextArea
                    //System.out.println(lyrics);
                    //lyricsTextArea.setText(song.getName());
                    lyricsTextArea.setText(lyrics);
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

    private void stopMidiFile() {
        try {
            if (player != null) {
                player.close();
                player = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == albumComboBox) {
            // Load the MIDI file names for the selected album
            String albumName = (String) albumComboBox.getSelectedItem();
            loadMidiFileNames(albumName);
        } else if (source == playButton) {
            // Play the selected MIDI file
            String midiName = (String) midiComboBox.getSelectedItem();
            playMidiFile(midiName);
        } else if (source == stopButton) {
            // Stop the currently playing MIDI file
            stopMidiFile();
        }
    }
}