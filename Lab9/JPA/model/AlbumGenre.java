package org.example.JPA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "album_genre")
public class AlbumGenre {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public AlbumGenre() {}

    public AlbumGenre(Album album, Genre genre) {
        this.album = album;
        this.genre = genre;
    }

    public Album getAlbum() {
        return album;
    }

    public Genre getGenre() {
        return genre;
    }
}

