import java.util.HashMap;

public class Solution {
    private HashMap<Location, Double> solutions;

    public Solution(HashMap solutions) {
        this.solutions = solutions;
    }

    @Override
    public String toString() {
        return "Solution{" + "\n" + "solutions=" + solutions + '}';
    }
}

