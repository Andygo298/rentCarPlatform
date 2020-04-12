package com.github.andygo298.rentCarPlatform.model;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isBlocked;

    public User(Long id, String firstName, String lastName, String email, boolean isBlocked) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isBlocked = false;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBlocked() {
        return isBlocked;
    }
}
