package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ExplorationMap {
    private static List<Token>[][] matrix;
    private static int sizeMatrix;

    public ExplorationMap(int size) {
        this.sizeMatrix = size;
        matrix = new List[sizeMatrix][sizeMatrix];
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                matrix[i][j] = new ArrayList<>();
            }
        }
    }

    public boolean visit(int row, int col, Robot robot) {
        synchronized (matrix[row][col]) {
            if (matrix[row][col].isEmpty() == true) {
//                the robot extract tokens
                List<Token> listOfTokens = robot.explore.getMem().extractTokens(7);
                //                and store the tokens in the cell(it becomes visited)
                matrix[row][col].addAll(listOfTokens);
                Robot.getNumberOfTokens().put(robot, Robot.getNumberOfTokens().get(robot) + 7);
//                System.out.println(listOfTokens);
//                System.out.println();
                //                display a success message
                //System.out.println(robot.getName() + " a vizitat cell-ul: row= " + row + " col= " + col);
            } else {
                //System.out.println(robot.getName()+" cell deja vizitat");
                //se alege alta linie pentru robot daca mai exista
                if (robot.row + 1 < sizeMatrix)
                    robot.row++;
            }
            return isFull();
        }
    }

    public static List<Token>[][] getMatrix() {
        return matrix;
    }

    public static boolean isFull() {
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                if (matrix[i][j].isEmpty()) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ExplorationMap{" +
                "matrix=" + Arrays.toString(matrix) +
                '}';
    }
}