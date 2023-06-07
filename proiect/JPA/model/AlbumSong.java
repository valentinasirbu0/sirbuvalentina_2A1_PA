package org.example.JPA.model;

import jakarta.persistence.*;


@Entity
@Table(name = "album_song")
@NamedQuery(name = "AlbumSong.findAlbumsforSong", query = "SELECT a FROM AlbumSong a WHERE a.song.id = :songId")
@NamedQuery(name = "AlbumSong.findSongsInAlbum", query = "SELECT a FROM AlbumSong a WHERE a.album.id = :albumId")
public class AlbumSong {

    @Id
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Id
    @ManyToOne
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
