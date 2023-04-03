package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private String name;
    private List<Document> docs = new ArrayList<>();

    public Catalog() {
    }

    public Catalog(String string) {
        this.name = string;
    }

    public List<Document> getDocs() {
        return docs;
    }

    public void setDocs(List<Document> docs) {
        this.docs = docs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void add(Document doc) {

        docs.add(doc);
    }

    public Document findById(String id) {
        return docs.stream()
                .filter(d -> d.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "name='" + name + '\'' +
                ", docs=" + docs +
                '}';
    }
}