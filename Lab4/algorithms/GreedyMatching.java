package org.example.algorithms;

import org.example.model.Project;
import org.example.model.Student;

import java.util.*;

public class GreedyMatching extends Algorithms {
    Map<Student, Project> prefMap = new HashMap<>();

    public void solve() {
        for (Student student : super.students) {
            for (Project project : student.getPreferences()) {
                if (!prefMap.containsKey(student) && !prefMap.containsValue(project) && student.getPreferences().contains(project)) {
                    prefMap.put(student, project);
                }
            }
        }
        for (Student s : students) {
            if (!prefMap.containsKey(s)) {
                prefMap.put(s, null);
            }
        }
    }

    @Override
    public String toString() {
        return "Matching{" +
                "prefMap=" + prefMap +
                '}';
    }
}
