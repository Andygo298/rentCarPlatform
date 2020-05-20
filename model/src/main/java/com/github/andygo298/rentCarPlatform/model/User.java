package com.github.andygo298.rentCarPlatform.model;

import java.util.HashSet;
import java.util.Set;

public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isBlocked;

    private AuthUser authUser;
    private Set<Payment> payments;
    private Set<Order> orders;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, boolean isBlocked) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isBlocked = false;
        this.payments = new HashSet<>();
        this.orders = new HashSet<>();
    }
//from converter
    public User(Long id, String firstName, String lastName, String email, boolean isBlocked, AuthUser authUser, Set<Payment> payments, Set<Order> orders) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isBlocked = isBlocked;
        this.authUser = authUser;
        this.payments = payments;
        this.orders = orders;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
