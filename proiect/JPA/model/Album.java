package org.example.JPA.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@NamedQuery(name = "Album.findByTitle", query = "SELECT a FROM Album a WHERE a.title LIKE :title")
@NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a")
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "year", nullable = false)
    private String year;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private Artist artist;

    public Album() {
    }

    public Album(String year, String title, Artist artist) {
        this.year = year;
        this.title = title;
        this.artist = artist;
    }

    public Integer getId() {
        return id;
    }

    public String getYear() {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Album album = (Album) obj;

        if (!Objects.equals(id, album.id)) return false;
        return Objects.equals(title, album.title);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

}
