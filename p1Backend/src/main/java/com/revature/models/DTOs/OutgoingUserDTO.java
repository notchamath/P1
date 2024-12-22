package com.revature.models.DTOs;

public class OutgoingUserDTO {
    private int userId;
    private String username;
    private String role;
    private String firstName;
    private String lastName;

    // Constructors, Getters, and Setters
    public OutgoingUserDTO() {
    }

    public OutgoingUserDTO(int userId, String username, String role, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}