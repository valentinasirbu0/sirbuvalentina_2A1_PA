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

    public AlbumGenre() {
    }

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

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        AlbumGenre that = (AlbumGenre) obj;

        if (album == null || genre == null || that.album == null || that.genre == null) return false;

        return album.equals(that.album) && genre.equals(that.genre);
    }

    @Override
    public int hashCode() {
        int result = album != null ? album.hashCode() : 0;
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }

}

