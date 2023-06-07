package org.example.JPA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_favourites")
@NamedQuery(name = "UserFavourite.findAllFavourites", query = "SELECT uf FROM UserFavourite uf WHERE uf.user.id = :userId"
)
public class UserFavourite {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    public UserFavourite() {
    }

    public UserFavourite(User user, Song song) {
        this.user = user;
        this.song = song;
    }

    // Getters and Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
