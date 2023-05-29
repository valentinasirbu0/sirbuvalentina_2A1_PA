package org.example.JPA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "album_artist")
public class AlbumArtist {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public AlbumArtist() {
    }

    public AlbumArtist(Album album, Artist artist) {
        this.album = album;
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

}
