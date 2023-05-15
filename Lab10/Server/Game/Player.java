package org.example.Game;

import java.io.PrintWriter;

public class Player {
    private final String name;
    private PieceColor color;
    private final PrintWriter out;
    private Long time;
    private Long startTime;

    public Player(String name, PrintWriter out) {
        this.out = out;
        this.name = name;
        this.time = 15000L;
    }

    public Long getTime() {
        return time;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public PieceColor getColor() {
        return color;
    }

    public Piece getPiece(int row, int col) {
        return new Piece(row, col, this);
    }

    public void startTime() {
        startTime = System.currentTimeMillis();
    }

    public void countTime() {
        Long endTime = System.currentTimeMillis();
        time = time - (endTime - startTime);
        System.out.println(getName() + " has " + time + "seconds");
    }
}

