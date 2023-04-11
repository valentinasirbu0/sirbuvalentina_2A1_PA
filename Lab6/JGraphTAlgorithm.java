package org.example;

import org.jgrapht.Graph;
import org.jgrapht.GraphMetrics;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;

import static org.example.DrawingPanel.edges;

public class JGraphTAlgorithm {
    Graph<Integer, DefaultEdge> graph;

    public JGraphTAlgorithm() {
        graph = new SimpleGraph<>(DefaultEdge.class);
        createGraph(edges);
        long result = GraphMetrics.getNumberOfTriangles(graph);
        System.out.println("Result nr of triangles : " + result);
    }

    private void createGraph(List<AbstractEdge> edges) {
        for (AbstractEdge e : edges) {
            graph.addVertex(e.getLeftNode());
            graph.addVertex(e.getRightNode());
            graph.addEdge(e.getLeftNode(), e.getRightNode());

        }
    }
}

