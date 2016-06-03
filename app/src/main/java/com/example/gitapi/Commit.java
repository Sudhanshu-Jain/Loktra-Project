package com.example.gitapi;

/**
 * Created by sudhanshu on 2/6/16.
 */
public class Commit {
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;
    String sha;

    public Commit() {
    }

    public Commit(String username, String message, String sha) {
        this.username = username;
        this.message = message;
        this.sha = sha;
    }
}
