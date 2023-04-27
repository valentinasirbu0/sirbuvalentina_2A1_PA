package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.Robot.getNumberOfTokens;

public class Exploration {
    private final SharedMemory mem = new SharedMemory(10);
    private final ExplorationMap map = new ExplorationMap(5);
    private static final List<Robot> robots = new ArrayList<>();
    private int nrOfRobots = 0;
    private static long start;
    private static long end;

    public void startAll() {
        for (Robot robot : robots) {
            Thread thread = new Thread(robot, robot.getName());
            thread.start();
        }
    }

    public void startOne(String name) {
        for (Robot robot : robots) {
            if (robot.getName().equals(name)) {
                Thread thread = new Thread(robot, robot.getName());
                thread.start();
            }
        }
    }

    public void pauseAllSomeTime(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void pauseOneSomeTime(String name, int time) {
        for (Robot robot : robots) {
            if (robot.getName().equals(name)) {
                robot.sleep(time);
            }
        }
    }

    public void pauseAll() {
        for (Robot robot : robots) {
            robot.stop();
        }
    }

    public void pauseOne(String name) {
        for (Robot robot : robots) {
            if (robot.getName().equals(name)) {
                robot.stop();
            }
        }
    }

    public static void main(String args[]) {
        var explore = new Exploration();
        explore.addRobot(new Robot("Wall-E"));
        explore.addRobot(new Robot("R2D2"));
        explore.addRobot(new Robot("Optimus Prime"));
        start = System.currentTimeMillis();
        //30 secunde
        end = start + 30000;
        new Thread(() -> {
            do {
                Scanner scanner = new Scanner(System.in);
                System.out.println("enter an command: ");
                String input = scanner.nextLine();
                if (input.equals("startAll")) {
                    explore.startAll();
                }
                if (input.equals("startOne")) {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("enter a robot name: ");
                    String input2 = scanner1.nextLine();
                    explore.startOne(input2);
                }
                if (input.equals("pauseAllSomeTime")) {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("enter a time: ");
                    int input2 = scanner1.nextInt();
                    explore.pauseAllSomeTime(input2);
                }
                if (input.equals("pauseOneSomeTime")) {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("enter a robot name: ");
                    String input2 = scanner1.nextLine();
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("enter a time: ");
                    int input3 = scanner2.nextInt();
                    explore.pauseOneSomeTime(input2, input3);
                }
                if (input.equals("pauseAll")) {
                    explore.pauseAll();
                }
                if (input.equals("pauseOne")) {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("enter a robot name: ");
                    String input2 = scanner1.nextLine();
                    explore.pauseOne(input2);
                }
            } while (ExplorationMap.isFull() && timeKeeper(System.currentTimeMillis()));
            if (!ExplorationMap.isFull())
                viewMap();
            System.out.println("Time: " + (System.currentTimeMillis() - start));
        }).start();
    }

    private static boolean timeKeeper(long time) {
        if (time >= end) {
            System.out.println("Time reached: 30 seconds");
            return false;
        }
        return true;
    }

    private void addRobot(Robot robot) {
        robots.add(robot);
        getNumberOfTokens().put(robot, 0);
        nrOfRobots++;
    }

    public ExplorationMap getMap() {
        return this.map;
    }

    public SharedMemory getMem() {
        return mem;
    }

    public int getNrOfRobots() {
        return nrOfRobots;
    }

    public static void viewMap() {
        for (Robot robot : robots) {
            System.out.println(robot.getName() + ": extracted " + Robot.getNumberOfTokens().get(robot) + " tokens");
        }
    }
}