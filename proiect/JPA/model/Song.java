package org.example.JPA.model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Song.findByName", query = "SELECT a FROM Song a WHERE a.name LIKE :name")
@NamedQuery(name = "Song.findAll", query = "SELECT a FROM Song a")
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_data", nullable = false, columnDefinition = "bytea")
    private byte[] fileData;

    @Column(name = "path", nullable = false)
    private String path;

    public Song() {
    }

    public Song(String outputPath, String name, byte[] fileData) {
        this.name = name;
        this.fileData = fileData;
        this.path = outputPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
