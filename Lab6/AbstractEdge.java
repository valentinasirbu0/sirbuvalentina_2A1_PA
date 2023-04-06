package org.example;

import javax.swing.*;

public class AbstractEdge {
    private int index;
    private int leftNode;
    private int rightNode;
    private Boolean color;
    JLabel label;

    public AbstractEdge() {
        this.leftNode = -1;
        this.rightNode = -1;
        this.label = null;
        this.color = null;
        this.index = -1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Boolean getColor() {
        return color;
    }

    public void setColor(Boolean color) {
        this.color = color;
    }

    public int getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(int leftNode) {
        this.leftNode = leftNode;
    }

    public int getRightNode() {
        return rightNode;
    }

    public void setRightNode(int rightNode) {
        this.rightNode = rightNode;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }
}
