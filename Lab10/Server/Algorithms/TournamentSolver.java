package org.example.Algorithms;

import org.graph4j.Digraph;
import org.graph4j.alg.TopologicalSort;

import java.util.ArrayList;
import java.util.List;

public class TournamentSolver {

    public static List<Integer> findPlayerSequence(Digraph graph) {
        List<Integer> playerSequence = new ArrayList<>();
        // Perform topological sorting
        TopologicalSort topologicalSort = new TopologicalSort(graph);
        int[] sortedVertices = topologicalSort.sort();

        // Reverse the order to get the sequence where each player beats the next player
        for (int i = sortedVertices.length - 1; i >= 0; i--) {
            playerSequence.add(sortedVertices[i]);
        }
        return playerSequence;
    }

    public static void main(String[] args) {
        TournamentOrganizer.main();
        System.out.println(findPlayerSequence(TournamentOrganizer.graph));
    }
}
