package org.example.algorithms;

import org.example.model.Project;
import org.example.model.Student;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.alg.matching.HopcroftKarpMaximumMatching;
import org.graph4j.util.Matching;


public class MaxCardinalityMatchingGraph4J extends Algorithms {
    Matching matching;

    public void solve() {
        Graph g = GraphBuilder.numVertices(6).estimatedNumEdges(6).buildGraph();
        for (Student s : students) {
            for (Project p : s.getPreferences()) {
                g.addVertex(s);
                g.addVertex(p);
                g.addEdge(s, p);
            }
        }
        HopcroftKarpMaximumMatching hopkarp = new HopcroftKarpMaximumMatching(g);
        matching = hopkarp.getMatching();
    }

    @Override
    public String toString() {
        return "MaxCardinalityMatchingGraph4J{" +
                "matching=" + matching +
                '}';
    }
}
