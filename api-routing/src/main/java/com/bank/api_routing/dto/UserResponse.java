package com.bank.api_routing.dto;

public class UserResponse {

    private String id;
    private String email;
    private UserType role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public UserResponse(String id, String email, UserType role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public UserResponse() {
    }

}
