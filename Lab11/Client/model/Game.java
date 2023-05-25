package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Game {
    private long id;
    private Timestamp time;
    private String winner;

    public Game() {
        // Default no-argument constructor
    }

    @JsonCreator
    public Game(@JsonProperty("id") long id, @JsonProperty("time") Timestamp time, @JsonProperty("winner") String winner) {
        this.id = id;
        this.time = time;
        this.winner = winner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
