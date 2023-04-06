package org.example;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;

    public MainFrame() {
        super("My Drawing Application");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        canvas = new DrawingPanel(this);
        add(canvas, BorderLayout.CENTER);
        configPanel = new ConfigPanel(this);
        add(configPanel, BorderLayout.NORTH);
        controlPanel = new ControlPanel(this);
        add(controlPanel, BorderLayout.SOUTH);
        pack();
    }
}

