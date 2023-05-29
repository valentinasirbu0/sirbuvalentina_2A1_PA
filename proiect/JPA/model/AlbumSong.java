package org.example.JPA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "album_song")
public class AlbumSong {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    public AlbumSong() {
    }

    public AlbumSong(Album album, Song song) {
        this.album = album;
        this.song = song;
    }

    public Album getAlbum() {
        return album;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
