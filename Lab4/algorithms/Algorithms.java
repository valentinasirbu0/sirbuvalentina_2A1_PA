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
}
