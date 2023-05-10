package org.example.JPA.model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Genre.findByName", query = "SELECT a FROM Genre a WHERE a.name LIKE :name")
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}