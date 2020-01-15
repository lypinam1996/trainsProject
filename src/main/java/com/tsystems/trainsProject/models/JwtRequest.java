package com.tsystems.trainsProject.models;
import java.io.Serializable;

public class JwtRequest implements Serializable {

    private String login;
    private String password;
    public JwtRequest()
    {
    }
    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
    public String getUsername() {
        return this.login;
    }
    public void setUsername(String username) {
        this.login = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}