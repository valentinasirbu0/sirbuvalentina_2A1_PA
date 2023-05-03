package org.example.algorithms;

import org.jgrapht.Graph;
import org.jgrapht.alg.clique.DegeneracyBronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CliqueFinder {
    public Iterator<Set<Integer>> result;

    public CliqueFinder(Graph<Integer, DefaultEdge> graph) {
        DegeneracyBronKerboschCliqueFinder<Integer, DefaultEdge> finder = new DegeneracyBronKerboschCliqueFinder<>(graph);
        result = finder.maximumIterator();
    }

    public Set<Integer> returnCliques() {
        Set<Integer> data = new HashSet<>();
        while (result.hasNext()) {
            Set<Integer> clique = result.next();
            data.addAll(clique);
            //System.out.println("Clique: " + clique.toString());
        }
        return data;
    }
}
