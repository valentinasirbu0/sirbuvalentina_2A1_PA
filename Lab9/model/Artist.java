package org.example._9.model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Artist.findByName", query = "SELECT a FROM Artist a WHERE a.name LIKE :name")
@Table(name = "genres")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}