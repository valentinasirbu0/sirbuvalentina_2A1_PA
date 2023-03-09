import java.util.HashMap;

public class DijkstraAlgorithm extends Algorithm {
    private double[][] matrix;
    private HashMap<Location, Double> solutions;

    public DijkstraAlgorithm(Problem pb) {
        super.pb = pb;
        populateMatrix();
        solutions = new HashMap<>();

        double[] dist = new double[matrix.length];
        boolean[] visited = new boolean[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            dist[i] = Double.MAX_VALUE;
            visited[i] = false;
        }
        for (int i = 0; i < matrix.length; i++) {
            solutions.put(pb.getLocation(i), computeMinLength(i, 0, visited, dist));
        }
    }

    private double computeMinLength(int destination, int start, boolean[] visited, double[] dist) {
        double minDist = Double.MAX_VALUE;
        int minIndex = start;
        for (int j = 0; j < matrix.length; j++) {
            if (!visited[j] && dist[j] < minDist) {
                minDist = dist[j];
                minIndex = j;
            }
        }
        visited[minIndex] = true;
        for (int k = 0; k < matrix.length; k++) {
            if(k == start) dist[k] = 0;
            if (!visited[k] && matrix[minIndex][k] != 0 && dist[minIndex] != Integer.MAX_VALUE && dist[minIndex] + matrix[minIndex][k] < dist[k]) {
                dist[k] = dist[minIndex] + matrix[minIndex][k];
            }
        }
        return minDist;
    }


    public double findDirectPath(Location source, Location destination) {
        for (int i = 0; i < pb.getRoads().length; i++) {
            if (pb.getRoads(i).getA().equals(source) && pb.getRoads(i).getB().equals(destination) ||
                    pb.getRoads(i).getA().equals(source) && pb.getRoads(i).getB().equals(destination))
                return pb.getRoads(i).getLength();
        }
        return 0;
    }

    public void populateMatrix() {
        matrix = new double[pb.getLocations().length][pb.getLocations().length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                for (int k = 0; k < pb.getRoads().length; k++) {
                    double distance = findDirectPath(pb.getLocation(i), pb.getLocation(j));
                    if(distance > 0) {
                        setMatrixVariable(i, j, distance);
                        setMatrixVariable(j, i, distance);
                    }
                }
            }
        }
    }

    private void setMatrixVariable(int i, int j, double var) {
        matrix[i][j] = var;
    }

    @Override
    public Solution solve() {
        Solution sol = new Solution(solutions);
        return sol;
    }
}
