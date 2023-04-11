package org.example;

import java.util.ArrayList;
import java.util.List;

public class ExplorationMap {
    private final Cell[][] matrix;
    private final SharedMemory mem;

    public ExplorationMap(int n, SharedMemory mem) {
        matrix = new Cell[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = new Cell();
            }
        }
        this.mem = mem;
    }

    public int[] getRandomUnvisitedCell() {
        List<int[]> unvisitedCells = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (!matrix[i][j].isVisited()) { // adding to the list all the unvisited cells
                    unvisitedCells.add(new int[]{i, j});
                }
            }
        }
        if (!unvisitedCells.isEmpty()) {
            int[] randomCell = unvisitedCells.get((int) (Math.random() * unvisitedCells.size()));
            return new int[]{randomCell[0], randomCell[1]};
        } else {
            return null; //exploration complete
        }
    }


    public void visit(int row, int col, Robot robot) {
        //only one robot can visit the same cell at a time
        //to ensure this - we use the synchronized keyword
        synchronized (matrix[row][col]) {
            if (!matrix[row][col].isVisited()) {
                List<Token> tokens = mem.extractTokens(matrix.length);
                matrix[row][col].addTokens(tokens);
                matrix[row][col].setVisited(true);
                System.out.println(robot.getName() + " visited cell [" + row + ", " + col + "]");
            }
        }


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append("[").append(i).append(",").append(j).append("]: ");
                sb.append(matrix[i][j].getTokens().toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
