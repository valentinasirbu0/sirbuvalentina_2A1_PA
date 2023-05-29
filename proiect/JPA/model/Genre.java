package org.example.JPA.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@NamedQuery(name = "Genre.findByName", query = "SELECT a FROM Genre a WHERE a.name LIKE :name")
@NamedQuery(name = "Genre.findAll", query = "SELECT a FROM Genre a")
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Genre genre = (Genre) obj;

        if (!Objects.equals(id, genre.id)) return false;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}