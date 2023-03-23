package org.example.model;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public abstract class ProjectPreferences {
    private Vector<Project> preferences = new Vector<>();
    private int mediumValue;

    public void setPreferences(List<Student> students, Project... project) {

        Collections.addAll(this.preferences, project);
        setMediumValue(students);
    }

    public Vector<Project> getPreferences() {

        return preferences;
    }

    public int getMediumValue() {

        return mediumValue;
    }

    public void setMediumValue(List<Student> students) {
        int sum = 0;
        for (Student s : students) {
            sum += s.getPreferences().size();
        }
        this.mediumValue = sum / students.size();
    }
}
