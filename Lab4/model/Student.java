package org.example.model;

import java.util.Objects;

public class Student extends ProjectPreferences implements Comparable<Student> {
    private String name;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Student(String s) {

        this.setName(s);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int compareTo(Student s) {
        int diff = s.getPreferences().size() - this.getPreferences().size();
        if (diff != 0) {
            return diff;
        }
        return this.getName().compareTo(s.getName());
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
