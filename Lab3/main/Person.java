package main.java.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Person)) return false;
        Person other = (Person) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", degree='" + getDegree() + '\'' +
                '}';
    }
}