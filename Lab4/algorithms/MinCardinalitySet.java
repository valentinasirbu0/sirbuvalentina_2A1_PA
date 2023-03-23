package org.example.algorithms;

import org.example.model.Project;
import org.example.model.Student;

import java.util.*;

public class MinCardinalitySet extends Algorithms {
    TreeSet<Object> minSet;
    ArrayList<Student> newStudents;
    TreeSet<Project> newProjects;

    public void solve() {
        newStudents = new ArrayList<>(students);
        newProjects = new TreeSet<>(projects);
        minSet = new TreeSet<>();
        Object obj;

        while (!existConnection()) {
            obj = findMaxDegreeObject();
            if (obj != null) {
                minSet.add(obj);
            } else {
                break;
            }
        }
    }

    private Object findMaxDegreeObject() {
        Object obj = null;
        Collections.sort(newStudents);

        if (!newStudents.isEmpty() && !newProjects.isEmpty()) {
            if (newStudents.get(0).getPreferences().size() >= newProjects.first().getChosen()) {
                for (Project p : newStudents.get(0).getPreferences()) {
                    p.setChosen(-1);
                    if (p.getChosen() == 0) {
                        newProjects.remove(p);
                    }
                }
                obj = newStudents.get(0);
                newStudents.remove(newStudents.get(0));
            } else {
                obj = newProjects.first();
                newProjects.remove(newProjects.first());
            }
        }
        return obj;
    }

    private boolean existConnection() {
        boolean answer = false;
        for (Student s : newStudents) {
            if (minSet.contains(s)) {
                for (Project p : s.getPreferences()) {
                    if (minSet.contains(p)) {
                        answer = true;
                    }
                }
            }
        }
        return answer;
    }

    @Override
    public String toString() {
        return "MaxCardinalitySet{" +
                "result=" + minSet +
                '}';
    }
}
