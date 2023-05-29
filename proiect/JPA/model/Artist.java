package org.example.JPA.model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Artist.findByName", query = "SELECT a FROM Artist a WHERE a.name LIKE :name")
@NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a")
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public Artist() {}

    public Artist(String name) {
        this.name = name;
    }

    public Artist(Integer id, String name) {
        this.id = id;
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