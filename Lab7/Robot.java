package org.example;


import org.example.Exploration;
import org.example.ExplorationMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Robot implements Runnable {
    private String name;
    private boolean running = true;
    private int col = -1;
    public int row;
    private static Map<Robot, Integer> numberOfTokens = new HashMap<>();
    Exploration explore;

    public Robot(String name) {
        this.name = name;
        this.explore = new Exploration();
    }

    public static Map<Robot, Integer> getNumberOfTokens() {
        return numberOfTokens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //se alege o linie si se parcurge de acelasi robot
    public int algorithmExploring() {
        for (int i = 0; i < ExplorationMap.getMatrix().length; i++) {
            if (ExplorationMap.getMatrix()[i][0].isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public void run() {
        row = algorithmExploring();
        while (running) {
//            pick a new cell to explore
//            int row= (int) (Math.random()*(ExplorationMap.getMatrix().length));
//            int col=(int)(Math.random()*(ExplorationMap.getMatrix()[0].length));
            if (col + 1 == ExplorationMap.getMatrix().length) {
                col = 0;
                row = algorithmExploring();
                if (row == -1) {
                    running = false;
                    break;
                }
            } else
                col++;
            if (!explore.getMap().visit(row, col, this)) {
                running = false;
            }
            //System.out.println(this.name+" visited a new cell");
            //            make the robot sleep for some time
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}