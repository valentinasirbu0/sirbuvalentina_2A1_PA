package main.java.model;

import java.util.HashMap;
import java.util.Map;

public class Company extends AbstractNode {
    private String name;

    final private Map<Node, String> relationships = new HashMap<>();

    public Company(String name) {
        super();
        System.out.println("Company created");
        this.name = name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public boolean equals(Object obj) {

        return this == obj;
    }

    public void addRelationship(Object obj, String value) {
        if (obj.getClass().equals(Person.class) || obj.getClass().equals(Designer.class) || obj.getClass().equals(Programmer.class)) {
            relationships.put((Person) obj, value);
        }
    }

    public Map<Node, String> getRelationships() {

        return relationships;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", degree='" + getDegree() + '\'' +
                '}';
    }
}
