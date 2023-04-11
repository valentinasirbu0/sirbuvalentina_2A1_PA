package org.example;

import javax.swing.*;

public class AbstractEdge {
    private int index;
    private int leftNode;
    private int rightNode;
    private int color;
    JLabel label;

    public AbstractEdge() {
        this.leftNode = -1;
        this.rightNode = -1;
        this.label = null;
        this.color = -1;
        this.index = -1;
    }

    public AbstractEdge(int index, int left, int right) {
        this.index = index;
        this.leftNode = left;
        this.rightNode = right;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
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
