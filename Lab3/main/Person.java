package main.java.model;

import java.util.HashMap;
import java.util.Map;

public class Person extends AbstractNode {
    private String name;
    private String birthDate;
    final private Map<Node, String> relationships = new HashMap<>();

    public void addRelationship(Node node, String value) {

        relationships.put(node, value);
    }

    public Map<Node, String> getRelationships() {

        return relationships;
    }

    public Person() {

        System.out.println("Person created");
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String getName() {

        return name;
    }

    public String getBirthDate() {

        return birthDate;
    }

    public void setBirthDate(String birthDate) {

        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object obj) {

        return this == obj;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", degree='" + getDegree() + '\'' +
                '}';
    }
}