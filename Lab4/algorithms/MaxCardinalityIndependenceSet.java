package org.example.algorithms;

import org.example.model.Project;
import org.example.model.Student;

import java.util.*;

public class MaxCardinalityIndependenceSet extends Algorithms {
    private Set<Object> maxIndependentSet = new HashSet<Object>();

    public void solve() {
        maxIndependentSet = findMaxIndepSet(students);
    }

    private Set<Object> findMaxIndepSet(List<Student> graph) {
        if (graph.isEmpty()) {
            return new HashSet<>();
        }
        if (graph.size() == 1) {
            return new HashSet<>(graph);
        }

        Student current = graph.get(0);
        List<Student> graph2 = new ArrayList<>(graph);
        graph2.remove(current);

        Set<Object> res1 = findMaxIndepSet(graph2);
        for (Project p : current.getPreferences()) {
            if (graph2.contains(p)) {
                graph2.remove(p);
            }
        }
        Set<Object> res2 = new HashSet<>();
        res2.add(current);
        res2.addAll(findMaxIndepSet(graph2));

        if (res1.size() > res2.size())
            return res1;
        return res2;
    }

    @Override
    public String toString() {
        return "MaxCardinalityIndependenceSet{" +
                "maxIndependentSet=" + maxIndependentSet +
                '}';
    }
}
