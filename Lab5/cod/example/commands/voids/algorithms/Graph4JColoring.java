package org.example.commands.voids.algorithms;

import org.example.commands.voids.ListCommand;
import org.example.model.Catalog;
import org.example.model.Document;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.alg.coloring.VertexColoring;

public class Graph4JColoring implements ListCommand {
    Graph graph;

    public Graph4JColoring(Catalog catalog) {
        graph = GraphBuilder.numVertices(catalog.getDocs().size()).estimatedNumEdges(catalog.getDocs().size()).buildGraph();
        for (int i = 0; i < catalog.getDocs().size(); i++) {
            Document d = catalog.getDocs().get(i);
            for (int j = i + 1; j < catalog.getDocs().size(); j++) {
                Document d2 = catalog.getDocs().get(j);
                if (d != d2 && existConnection(d, d2) &&
                        !graph.containsEdge(Integer.parseInt(d.getId()), Integer.parseInt(d2.getId())) &&
                        !graph.containsVertex(Integer.parseInt(d.getId())) &&
                        !graph.containsVertex(Integer.parseInt(d2.getId()))) {
                    graph.addVertex(Integer.parseInt(d.getId()));
                    graph.addVertex(Integer.parseInt(d2.getId()));
                    graph.addEdge(Integer.parseInt(d.getId()), Integer.parseInt(d2.getId()));
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
        VertexColoring result = new VertexColoring(graph);
        System.out.println("Graph4JColoring{" + "result=" + result + '}');
    }

}
