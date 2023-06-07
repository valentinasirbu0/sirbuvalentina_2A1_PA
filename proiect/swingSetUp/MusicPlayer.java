package org.example.swingSetUp;

import javazoom.jl.player.Player;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.example.swingSetUp.PlayerPageInstructions.*;

public class MusicPlayer extends JFrame implements ActionListener {

    public static JComboBox<String> albumComboBox;
    public static JComboBox<String> midiComboBox;
    public static JComboBox<String> languageComboBox;
    public static JComboBox<String> favouritesComboBox;
    private JComboBox<?> lastModifiedComboBox = null;
    private JButton playButton;
    private JButton stopButton;
    private JButton addToFavoritesButton;  // New button for adding to favorites
    private Sequencer sequencer;
    private Statement statement;
    private ResultSet resultSet;
    public static Player player;
    public static JTextArea lyricsTextArea;
    public static Connection connection;

    public MusicPlayer(Connection connection) {
        super("Music Player");
        this.connection = connection;

        try {
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
            // Create the language combo box
            languageComboBox = new JComboBox<>();
            languageComboBox.setPreferredSize(new Dimension(200, 25));

            favouritesComboBox = new JComboBox<>();
            favouritesComboBox.setPreferredSize(new Dimension(200, 25));

            playButton = new JButton("Play");
            playButton.addActionListener(this);

            stopButton = new JButton("Stop");
            stopButton.addActionListener(this);

            addToFavoritesButton = new JButton("Add to Favorites"); // New button
            addToFavoritesButton.addActionListener(this);

            JPanel controlPanel = new JPanel(new GridLayout(4, 2));
            controlPanel.add(new JLabel("Album:"));
            controlPanel.add(albumComboBox);
            controlPanel.add(new JLabel("MP3 File:"));
            controlPanel.add(midiComboBox);
            controlPanel.add(new JLabel("Language:"));
            controlPanel.add(languageComboBox);
            controlPanel.add(new JLabel("Favourites:"));
            controlPanel.add(favouritesComboBox);

            loadLanguageOptions();

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
            buttonPanel.add(addToFavoritesButton);  // Add the new button

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
            loadFavouritesList();

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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == albumComboBox) {
            // Load the MIDI file names for the selected album
            String albumName = (String) albumComboBox.getSelectedItem();
            loadMidiFileNames(albumName);
        } else if (source == playButton) {
            // Determine the source of the last modified field (favorites or MIDI files)
            JComboBox<?> sourceComboBox = (lastModifiedComboBox != null) ? lastModifiedComboBox : midiComboBox;

            String selectedSong = (String) sourceComboBox.getSelectedItem();
            if (selectedSong != null) {
                playMidiFile(selectedSong);
            } else {
                System.out.println("No song selected to play.");
            }
        } else if (source == stopButton) {
            // Stop the currently playing MIDI file
            stopMidiFile();
        } else if (source == addToFavoritesButton) {  // Handle the add to favorites button click
            addToFavorites(connection, (String) midiComboBox.getSelectedItem());
            loadFavouritesList();
        }

        // Update the last modified combo box
        if (source == favouritesComboBox || source == midiComboBox) {
            lastModifiedComboBox = (JComboBox<?>) source;
        }
    }
}
