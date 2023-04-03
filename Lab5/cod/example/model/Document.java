package org.example.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Document implements Serializable {
    private File file;
    private String id;
    private String title;
    private String location;
    private int colour = -1;
    private List<String> tags = new ArrayList<>();

    public Document() {
    }

    public Document(File file, String title, String... data) {
        this.file = file;
        this.title = title;
        if (data.length == 1) {
            this.id = data[0];
        } else {
            this.id = data[0];
            this.location = data[1];
        }
    }

    public Document(File file, String title, String id, int tag1, int tag2) {
        this.file = file;
        this.title = title;
        this.id = id;
        setTags(Integer.toString(tag1), Integer.toString(tag2));
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTags(String... tags) {
        for (String tag : tags) {
            this.tags.add(tag);
        }
    }

    public List<String> getTags() {
        return tags;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", tags=" + tags +
                '}';
    }
}