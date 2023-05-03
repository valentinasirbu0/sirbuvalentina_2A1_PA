package org.example.algorithms;

import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.ClusteringCoefficient;
import org.jgrapht.graph.DefaultEdge;

import java.util.Map;

public class Clustering {
    public Map<Integer,Double> result;

    public Clustering(Graph<Integer, DefaultEdge> graph) {
        ClusteringCoefficient cluster = new ClusteringCoefficient<>(graph);
        result = cluster.getScores();
    }
}
