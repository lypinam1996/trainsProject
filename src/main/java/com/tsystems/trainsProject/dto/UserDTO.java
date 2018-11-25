package com.tsystems.trainsProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
    @JsonProperty("login")
    private String login;
    @JsonProperty("role")
    private String role;

    public UserDTO(String login, String role) {
        this.login = login;
        this.role = role;
    }

    public UserDTO() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
