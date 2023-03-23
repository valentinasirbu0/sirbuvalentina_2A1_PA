package org.example.model;

import java.util.Objects;

public class Project implements Comparable<Project> {
    private String name;
    private int chosen = 0;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getChosen() {
        return chosen;
    }

    public void setChosen(int c) {
        if (c == 1) {
            this.chosen++;
        }
        if (c == -1) {
            this.chosen--;
        }
    }

    public Project(String s) {
        this.setName(s);
    }

    public Project getProject(String name) {
        if (this.getName() == name) {
            return this;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Project)) return false;
        Project other = (Project) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int compareTo(Project o) {
        int diff = this.getChosen() - o.getChosen();
        if (diff != 0) {
            return diff;
        }
        return this.getName().compareTo(o.getName());
    }


    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", chosen=" + chosen +
                '}';
    }
}
