package com.github.andygo298.rentCarPlatform.model;

public class AuthUser {
    private Long id;
    private String login;
    private String password;
    private Role role;
    private Long userId;

    public AuthUser(Long id, String login, String password, Role role, Long userId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Long getUserId() {
        return userId;
    }
}
