package org.example;

import static org.example.DrawingPanel.*;

public class Game {
    protected static boolean turn = false;

    protected static boolean existTriangleOfColor() {
        for (AbstractEdge e : edges) {
            for (Integer n : nodes.keySet()) {
                if (existsLine(e.getLeftNode(), n) && existsLine(n, e.getRightNode())) {
                    if (e.getColor().equals(getColorOfLine(e.getLeftNode(), n)) && e.getColor().equals(getColorOfLine(n, e.getRightNode()))) {
                        return e.getColor();
                    }
                }
            }
        }
        return false;
    }

    private static boolean getColorOfLine(Integer i1, Integer i2) {
        for (AbstractEdge e : edges) {
            if ((e.getLeftNode() == i1 && e.getRightNode() == i2) ||
                    (e.getLeftNode() == i2 && e.getRightNode() == i1)) {
                return e.getColor();
            }
        }
        return false;
    }
}
