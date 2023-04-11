package org.example;

import java.awt.*;

import static org.example.DrawingPanel.*;

public class Game {
    protected static boolean turn = true;

    protected static int existTriangleOfColor() {
        for (AbstractEdge e : edges) {
            for (int n : nodes.keySet()) {
                if (existsLine(e.getLeftNode(), n) && existsLine(n, e.getRightNode())) {
                    if (e.getColor() == getColorOfLine(e.getLeftNode(), n) && e.getColor() == getColorOfLine(n, e.getRightNode())) {
                        return e.getColor();
                    }
                }
            }
        }
        return -1;
    }

    private static int getColorOfLine(int i1, int i2) {
        for (AbstractEdge e : edges) {
            if ((e.getLeftNode() == i1 && e.getRightNode() == i2) ||
                    (e.getLeftNode() == i2 && e.getRightNode() == i1)) {
                return e.getColor();
            }
        }
        return -1;
    }

    protected static void playWithAi() {
        boolean found = false;
        for (AbstractEdge e : edges) {
            for (int n : nodes.keySet()) {
                if (existsLine(e.getLeftNode(), n) && existsLine(n, e.getRightNode()) && !found) {
                    if (getColorOfLine(e.getLeftNode(), n) == getColorOfLine(n, e.getRightNode()) && getColorOfLine(n, e.getRightNode()) != -1 && e.getColor() == -1) {
                        if (e.getColor() == -1) {
                            found = true;
                            e.getLabel().setBackground(Color.RED);
                            e.setColor(0);
                            MyMouseListener.colored++;
                            break; // break out of inner loop once a label is found
                        }
                    }
                }
            }
        }


        if (!found) {
            for (AbstractEdge e : edges) {
                if (e.getColor() == -1) {
                    e.getLabel().setBackground(Color.RED);
                    e.setColor(0);
                    MyMouseListener.colored++;
                    break;
                }
            }
        }


    }
}
