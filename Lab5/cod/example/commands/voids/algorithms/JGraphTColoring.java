package org.example.commands.voids.algorithms;

import org.example.commands.voids.ListCommand;

import org.example.model.Catalog;
import org.example.model.Document;
import org.jgrapht.*;
import org.jgrapht.alg.color.BrownBacktrackColoring;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class JGraphTColoring implements ListCommand {
    Graph<Document, Document> graph;

    public JGraphTColoring(Catalog catalog) {
        graph = new SimpleGraph(DefaultEdge.class);
        for (int i = 0; i < catalog.getDocs().size(); i++) {
            Document d = catalog.getDocs().get(i);
            for (int j = i + 1; j < catalog.getDocs().size(); j++) {
                Document d2 = catalog.getDocs().get(j);
                if (existConnection(d, d2) && !graph.containsEdge(d, d2) && !graph.containsEdge(d2, d)
                        && !graph.containsVertex(d) && !graph.containsVertex(d2)) {
                    graph.addVertex(d);
                    graph.addVertex(d2);
                    graph.addEdge(d, d2);
                }
            }
        }
    }

    private boolean existConnection(Document d1, Document d2) {
        for (String tag1 : d1.getTags()) {
            for (String tag2 : d2.getTags()) {
                if (tag1.equals(tag2) && tag1 != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        BrownBacktrackColoring<Document, Document> coloring = new BrownBacktrackColoring<>(graph);
        VertexColoringAlgorithm.Coloring result = coloring.getColoring();
        System.out.println("JGraphTColoring{" + "result=" + result + '}');
    }

}