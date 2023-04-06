package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel dotsLabel, linesLabel;
    JSpinner dotsSpinner;
    JComboBox linesCombo;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        dotsLabel = new JLabel("Number of dots:");
        dotsSpinner = new JSpinner(new SpinnerNumberModel(6, 3, 100, 1));
        linesLabel = new JLabel("Number of lines");
        linesCombo = new JComboBox<>();

        dotsSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = (int) dotsSpinner.getValue();
                int maxEdges = (value % 2 == 0) ? (value * (value - 1)) / 2 : ((value - 1) * (value - 1)) / 2 + (value - 1) / 2;
                linesCombo.removeAllItems();
                for (int i = 0; i <= maxEdges; i++) {
                    linesCombo.addItem(i);
                }
                DrawingPanel.numVertices = value;
                updateNumEdges();
            }
        });

        linesCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateNumEdges();
            }
        });


        DrawingPanel.numVertices = (int) dotsSpinner.getValue();
        Object selectedItem = linesCombo.getSelectedItem();
        if (selectedItem != null) {
            DrawingPanel.numEdges = Integer.parseInt(selectedItem.toString());
            DrawingPanel.refresh();
        }

        DrawingPanel.numVertices = (int) dotsSpinner.getValue();
        updateNumEdges();

        add(dotsLabel);
        add(dotsSpinner);
        add(linesLabel);
        add(linesCombo);

    }

    private void updateNumEdges() {
        int value = (int) dotsSpinner.getValue();
        Object selectedItem = linesCombo.getSelectedItem();
        if (selectedItem != null) {
            int numEdges = Integer.parseInt(selectedItem.toString());
            int maxEdges = (value % 2 == 0) ? (value * (value - 1)) / 2 : ((value - 1) * (value - 1)) / 2 + (value - 1) / 2;
            if (numEdges > maxEdges) {
                DrawingPanel.numEdges = maxEdges;
                linesCombo.setSelectedItem(maxEdges);
            } else {
                DrawingPanel.numEdges = numEdges;
            }
            DrawingPanel.refresh();
        }
    }

}