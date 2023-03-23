package org.example.algorithms;

import org.example.model.Project;
import org.example.model.Student;

import java.util.List;
import java.util.Set;

public class Algorithms {
    public static List<Student> students;
    public static Set<Project> projects;

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {

        this.students = students;

    }

    public void testAlgorithms(int n) {
        System.out.println("\n");
        GreedyMatching greedyMatching = new GreedyMatching();
        long startTime = System.nanoTime();
        greedyMatching.solve();
        long totalTime = System.nanoTime() - startTime;
        System.out.println("Greedy  : " + totalTime/(n*1000) + " sec");

        MaxCardinalityMatchingGraph4J graph4J = new MaxCardinalityMatchingGraph4J();
        startTime = System.nanoTime();
        graph4J.solve();
        totalTime = System.nanoTime() - startTime;
        System.out.println("Graph4J : " + totalTime/(n*1000) + " sec");

        MaxCardinalityMatchingJGraphT jGraphT = new MaxCardinalityMatchingJGraphT();
        startTime = System.nanoTime();
        jGraphT.solve();
        totalTime = System.nanoTime() - startTime;
        System.out.println("JGraphT : " + totalTime/(n*1000) + " sec");
        System.out.println("\n");
    }
}
