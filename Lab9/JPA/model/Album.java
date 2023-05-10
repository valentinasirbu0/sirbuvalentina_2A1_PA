package org.example.JPA.model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Album.findByTitle", query = "SELECT a FROM Album a WHERE a.title LIKE :title")
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable = false)
    private Artist artist;

    public Album() {}

    public Album(Integer year, String title, Artist artist) {
        this.year = year;
        this.title = title;
        this.artist = artist;
    }

    public Integer getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
