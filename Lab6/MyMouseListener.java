package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static org.example.DrawingPanel.edges;
import static org.example.DrawingPanel.numEdges;
import static org.example.Game.existTriangleOfColor;
import static org.example.Game.turn;

public class MyMouseListener implements MouseListener {
    static JLabel label;
    private int colored;

    public MyMouseListener(JLabel label) {
        this.label = label;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed by player: " + turn);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released by player: " + turn);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered");
        ((JLabel) e.getSource()).setBackground(Color.YELLOW);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Exited label");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked label");
        if (turn) {
            ((JLabel) e.getSource()).setBackground(Color.BLUE);
            edges.get(indexOfLabel((JLabel) e.getSource())).setColor(turn);
            colored++;
        } else {
            ((JLabel) e.getSource()).setBackground(Color.RED);
            edges.get(indexOfLabel((JLabel) e.getSource())).setColor(turn);
            colored++;
        }
        turn = !turn;
        if (existTriangleOfColor()) {
            System.out.println("Game ended");
        }else if(colored == numEdges){
            System.out.println("There is no winner");
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


}