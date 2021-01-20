package com.udacity.cloudstorage.model;

public class Credential {

    private Integer id;
    private String url;
    private String key;
    private Integer userId;
    private String username;
    private String password;

    public Credential() {}

    public Credential(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public Credential(String url, String username, String password, Integer userId) {
        this.url = url;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public Credential(Integer id, String url, String username, String password, Integer userId) {
        this.id = id;
        this.url = url;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public Credential(Integer id, String key, String url, String username, String password, Integer userId) {
        this.id = id;
        this.key = key;
        this.url = url;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
