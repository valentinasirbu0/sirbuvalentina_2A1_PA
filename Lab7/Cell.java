package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private boolean visited;
    private final List<Token> tokens;

    public Cell() {
        visited = false;
        tokens = new ArrayList<>();
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void addTokens(List<Token> tokens) {
        this.tokens.addAll(tokens);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "visited=" + visited +
                ", tokens=" + tokens +
                '}';
    }
}
