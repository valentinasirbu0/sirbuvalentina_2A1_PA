package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.example.DrawingPanel.*;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");
    JButton newPageButton = new JButton("New Page");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(new GridLayout(1, 4));
        exitBtn.addActionListener(this::exitGame);
        loadBtn.addActionListener(this::loadGraph);
        saveBtn.addActionListener(this::saveGraph);
        newPageButton.addActionListener(this::newPage);
        add(exitBtn);
        add(saveBtn);
        add(loadBtn);
        add(newPageButton);
    }

    private void newPage(ActionEvent e) {
        DrawingPanel.refresh();
    }

    private void saveGraph(ActionEvent e) {
        try (FileOutputStream fileOut = new FileOutputStream("output.ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(instance);

        } catch (IOException ev) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + ev.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            ev.printStackTrace();
        }

        // Export an image of the DrawingPanel
        BufferedImage image = new BufferedImage(instance.getWidth(), instance.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        instance.paint(g2d);
        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File("output.png"));
        } catch (IOException ev) {
            JOptionPane.showMessageDialog(null, "Error saving image: " + ev.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            ev.printStackTrace();
        }

        System.out.println("DrawingPanel saved to output.ser and output.png");
    }

    private void loadGraph(ActionEvent e) {
        DrawingPanel panel = null;
        try (FileInputStream fileIn = new FileInputStream("output.ser");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            panel = (DrawingPanel) objectIn.readObject();
        } catch (IOException | ClassNotFoundException ev) {
            ev.printStackTrace();
        }

        // Display the DrawingPanel in a JFrame
        if (panel != null) {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        }
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }
}
