package com.udacity.cloudstorage.model;

public class Note {

    private Integer id;
    private String title;
    private Integer userId;
    private String description;

    public Note() {}

    public Note(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public Note(Integer noteId, String title, String description, Integer userId) {
        this.id = noteId;
        this.title = title;
        this.userId = userId;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
