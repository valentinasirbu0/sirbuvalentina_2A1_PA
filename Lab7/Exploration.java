package org.example;

import java.util.ArrayList;
import java.util.List;

public class Exploration {


    private static final SharedMemory mem = new SharedMemory(2);
    private static final ExplorationMap map = new ExplorationMap(2, mem);
    private final List<Robot> robots = new ArrayList<>();
    private volatile boolean allCellsVisited = false;

    public void addRobot(Robot robot) {
        robots.add(robot);
    }

    public void start() {
        for (Robot robot : robots) {
            new Thread(robot).start();
        }
    }

    public synchronized void setAllCellsVisited() {
        allCellsVisited = true;
        for (Robot robot : robots) {
            robot.setRunning(false);
        }
    }

    public synchronized boolean areAllCellsVisited() {
        return allCellsVisited;
    }

    public ExplorationMap getMap() {
        return map;
    }

    public static void main(String[] args) {
        var explore = new Exploration();
        System.out.println("Initial shared memory: " + mem);

        explore.addRobot(new Robot("robot 1", explore));
        explore.addRobot(new Robot("robot 2", explore));
        explore.addRobot(new Robot("robot 3", explore));
        explore.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(map);
    }
}

