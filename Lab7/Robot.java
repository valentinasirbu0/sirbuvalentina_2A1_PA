package org.example;

public class Robot implements Runnable {
    private String name;
    private boolean running = true; //by default, the robot is running
    private int row;
    private int col;
    private Exploration explore;

    public Robot(String name, Exploration explore) {
        this.name = name;
        this.explore = explore;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setExploration(Exploration explore) {
        this.explore = explore;
    }

    public void run() {
        while (running && !explore.areAllCellsVisited()) {
            int[] newPos = explore.getMap().getRandomUnvisitedCell();
            if (newPos == null) {
                // we visited all cells and we terminate the robot
                running = false;
                explore.setAllCellsVisited();
                break;
            }
            int newRow = newPos[0];
            int newCol = newPos[1];

            //visit the cell
            explore.getMap().visit(newRow, newCol, this);

            //sleepy sleep
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("%s terminated %n", name);
    }

    public String getName() {
        return this.name;

    }
}