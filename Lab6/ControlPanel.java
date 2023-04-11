package org.example;

import org.example.DrawingPanel;
import org.example.MainFrame;

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
        BufferedImage image = new BufferedImage(instance.getWidth(), instance.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        instance.paint(g);
        g.dispose();

        try {
            ImageIO.write(image, "png", new File("output.png"));
        } catch (IOException ev) {
            JOptionPane.showMessageDialog(null, "Error saving image: " + ev.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            ev.printStackTrace();
        }
        System.out.println("Game saved to output.png");
    }

    private void loadGraph(ActionEvent e) {
        try {
            DrawingPanel.image = ImageIO.read(new File("C:\\Users\\Valea\\Desktop\\java\\_6\\output.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("Game loaded successfully");
        repaint();
    }


    private void exitGame(ActionEvent e) {
        frame.dispose();
    }
}
