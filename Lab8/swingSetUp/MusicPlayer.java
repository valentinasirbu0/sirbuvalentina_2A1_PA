package org.example.swingSetUp;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.sql.*;

public class MusicPlayer extends JFrame implements ActionListener {

    private JComboBox<String> albumComboBox;
    private JComboBox<String> midiComboBox;
    private JButton playButton;
    private JButton stopButton;
    private Sequencer sequencer;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

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

            JPanel controlPanel = new JPanel(new GridLayout(1, 4));
            controlPanel.add(new JLabel("Album:"));
            controlPanel.add(albumComboBox);
            controlPanel.add(new JLabel("MIDI File:"));
            controlPanel.add(midiComboBox);
            controlPanel.add(playButton);
            controlPanel.add(stopButton);

            // Add components to JFrame
            getContentPane().add(controlPanel, BorderLayout.NORTH);
            pack();
            setLocationRelativeTo(null);

            // Load the album names into the album combo box
            resultSet = statement.executeQuery("SELECT DISTINCT id, title FROM albums ORDER BY id ASC");
            while (resultSet.next()) {
                albumComboBox.addItem(resultSet.getString("title").toLowerCase());
                //System.out.println(resultSet.getString("title"));
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
                // check if the table exists
                PreparedStatement checkTableStmt = connection.prepareStatement("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = ?");
                checkTableStmt.setString(1, albumName);
                ResultSet checkTableResult = checkTableStmt.executeQuery();
                checkTableResult.next();
                int tableCount = checkTableResult.getInt(1);

                if (tableCount > 0) {
                    // table exists, load file names
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT file_name FROM " + albumName);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        midiComboBox.addItem(resultSet.getString("file_name"));
                    }
                } else {
                    System.out.println("Table " + albumName + " does not exist.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMidiFile(String midiName) {
        try {
            // Load the MIDI file from the database and play it
            String albumName = (String) albumComboBox.getSelectedItem();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT file_data FROM " + albumName + " WHERE file_name = ?");
            preparedStatement.setString(1, midiName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] midiData = resultSet.getBytes("file_data");
                sequencer.setSequence(MidiSystem.getSequence(new ByteArrayInputStream(midiData)));
                sequencer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopMidiFile() {
        sequencer.stop();
        sequencer.setMicrosecondPosition(0);
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