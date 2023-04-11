package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static org.example.DrawingPanel.*;
import static org.example.Game.*;

public class MyMouseListener implements MouseListener {
    static JLabel label;
    protected static int colored = 0;

    public MyMouseListener(JLabel label) {
        this.label = label;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Clicked label");
        if (turn) {
            ((JLabel) e.getSource()).setBackground(Color.BLUE);
            edges.get(indexOfLabel((JLabel) e.getSource())).setColor(1);
            colored++;
            System.out.println("You made your choice");
            evalStateOfGame();
            turn = !turn;
            playWithAi();
            System.out.println("AI made its choice"); //plays with blue
            turn = !turn;
            evalStateOfGame();
        }
    }

    private int indexOfLabel(JLabel e) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getLabel().equals(e)) {
                return i;
            }
        }
        return -1;
    }

    private void evalStateOfGame() {
        if (existTriangleOfColor() != -1) {
            System.out.println("Game ended");
            if (existTriangleOfColor() == 1) {         //BLUE 0     RED 1
                System.out.println("Color BLUE won");
            } else {
                System.out.println("Color RED won");
            }
        } else if (colored == numEdges) {
            System.out.println("There is no winner");
        }
    }

}