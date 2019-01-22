package com.example.youssefiibrahim.musicsheetgenerationapp.Model;

import java.util.List;

public class User {
    private String username;
    private String email;
    private List<String> savedLinks;

    public User() {
    }

    public User(String username, String email, List<String> savedLinks) {

        this.username = username;
        this.email = email;
        this.savedLinks = savedLinks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getSavedLinks() {
        return savedLinks;
    }

    public void setSavedLinks(List<String> savedLinks) {
        this.savedLinks = savedLinks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
