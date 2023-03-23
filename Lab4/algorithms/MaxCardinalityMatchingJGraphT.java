package org.example.algorithms;

import org.example.model.Project;
import org.example.model.Student;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashSet;
import java.util.Set;


public class MaxCardinalityMatchingJGraphT extends Algorithms {
    MatchingAlgorithm.Matching<Object, DefaultEdge> matching;

    public void solve() {
        Set students = new HashSet(super.students);
        Set projects = new HashSet(super.projects);
        Graph<Object, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        for (Student s : super.students) {
            for (Project p : s.getPreferences()) {
                graph.addVertex(s);
                graph.addVertex(p);
                graph.addEdge(s, p);
            }
        }
        HopcroftKarpMaximumCardinalityBipartiteMatching<Object, DefaultEdge> hopkarp =
                new HopcroftKarpMaximumCardinalityBipartiteMatching<>(graph, students, projects);
        matching = hopkarp.getMatching();
    }

    @Override
    public String toString() {
        return "MaxCardinalityMatching{" +
                "matching=" + matching +
                '}';
    }
}
