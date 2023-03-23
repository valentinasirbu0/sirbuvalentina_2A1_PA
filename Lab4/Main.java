package org.example;

import com.github.javafaker.Faker;
import org.example.algorithms.*;
import org.example.model.Project;
import org.example.model.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<Student> students = new LinkedList<>(createStudents(2));
        Set<Project> projects = new TreeSet<>(createProjects(2));
        setPreferences(students, projects, 2);

        students.forEach(System.out::println);
        projects.forEach(System.out::println);

        System.out.println("\nStudents with a nr of projects less than average:");
        students.stream()
                .filter(Student -> Student.getPreferences().size() < Student.getMediumValue())
                .forEach(System.out::println);


        Algorithms algorithm = new Algorithms();
        List<Student> studentsTest = new LinkedList<>();
        Set<Project> projectsTest = new TreeSet<>();
        problemGenerator(algorithm, studentsTest, projectsTest,(int)Math.floor(Math.random() * (1005 - 1000 + 1) + 1000));


        algorithm.setStudents(students);
        algorithm.setProjects(projects);

        GreedyMatching matching = new GreedyMatching();
        matching.solve();
        System.out.println(matching);

        MaxCardinalityMatchingJGraphT maxJGraphT = new MaxCardinalityMatchingJGraphT();
        maxJGraphT.solve();
        System.out.println(maxJGraphT);

        MaxCardinalityMatchingGraph4J maxGraph4J = new MaxCardinalityMatchingGraph4J();
        maxGraph4J.solve();
        System.out.println(maxGraph4J);

        MaxCardinalityIndependenceSet maxIndSet = new MaxCardinalityIndependenceSet();
        maxIndSet.solve();
        System.out.println(maxIndSet);

        MinCardinalitySet minSet = new MinCardinalitySet();
        minSet.solve();
        System.out.println(minSet);


    }

    public static void problemGenerator(Algorithms algorithm, List<Student> studentsTest, Set<Project> projectsTest,int range) {
        studentsTest = new LinkedList<>(createStudents(range));
        projectsTest = new TreeSet<>(createProjects(range));
        setPreferences(studentsTest, projectsTest, range);
        algorithm.setStudents(studentsTest);
        algorithm.setProjects(projectsTest);
        System.out.println("Statistics for 1000 vertices:");
        algorithm.testAlgorithms(range);
    }


    public static List createStudents(int range) {
        Faker faker = new Faker();
        List<Student> students = new LinkedList<>(Arrays.asList(
                IntStream.rangeClosed(0, range)
                        .mapToObj(i -> new Student(faker.name().firstName()))
                        .toArray(Student[]::new)
        ));
        return students.stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    public static Set createProjects(int range) {
        Faker faker = new Faker();
        return new TreeSet<>(Arrays.asList(
                IntStream.rangeClosed(0, range)
                        .mapToObj(i -> new Project(faker.name().firstName()))
                        .toArray(Project[]::new)
        ));
    }

    public static void setPreferences(List<Student> students, Set<Project> projects, int range) {
        int i = 0;
        for (Student s : students) {
            int j = range;
            for (Project proj : projects) {
                if (i <= j && j >= 0) {
                    students.get(i).setPreferences(students, proj);
                    proj.setChosen(1);
                }
                j--;
            }
            i++;
        }
    }
}

