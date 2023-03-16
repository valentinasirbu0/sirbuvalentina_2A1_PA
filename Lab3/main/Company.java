package main.java.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Company)) return false;
        Company other = (Company) obj;
        return Objects.equals(this.name, other.name);
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
