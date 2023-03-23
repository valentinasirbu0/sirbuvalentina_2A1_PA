package org.example.algorithms;

public class Statistics {
    static public void testAlgorithms(int n) {
        System.out.println("\n");
        GreedyMatching greedyMatching = new GreedyMatching();
        long startTime = System.nanoTime();
        greedyMatching.solve();
        long totalTime = System.nanoTime() - startTime;
        System.out.println("Greedy  : " + totalTime/(n*1000) + " sec");

        MaxCardinalityMatchingGraph4J graph4J = new MaxCardinalityMatchingGraph4J();
        startTime = System.nanoTime();
        graph4J.solve();
        totalTime = System.nanoTime() - startTime;
        System.out.println("Graph4J : " + totalTime/(n*1000) + " sec");

        MaxCardinalityMatchingJGraphT jGraphT = new MaxCardinalityMatchingJGraphT();
        startTime = System.nanoTime();
        jGraphT.solve();
        totalTime = System.nanoTime() - startTime;
        System.out.println("JGraphT : " + totalTime/(n*1000) + " sec");
        System.out.println("\n");
    }
}
