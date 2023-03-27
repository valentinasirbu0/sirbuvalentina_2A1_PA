package org.example;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Document implements Serializable {
    private File file;
    private String id;
    private String title;
    private String location;
    private Map<String, Object> tags = new HashMap<>();

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

    public void addTag(String key, Object obj) {
        tags.put(key, obj);
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