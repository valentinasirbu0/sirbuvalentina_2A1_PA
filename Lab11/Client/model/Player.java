package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
    private long id;
    private String name;
    private String color;

    public Player() {
        // Default no-argument constructor
    }

    @JsonCreator
    public Player(@JsonProperty("name") String name) {
        this.id = 1;
        this.name = name;
        this.color = "White";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
