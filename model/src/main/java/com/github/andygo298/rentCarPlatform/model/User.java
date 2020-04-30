package com.github.andygo298.rentCarPlatform.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isBlocked;

    private AuthUser authUser;
    private Set<Payment> payments;


    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, boolean isBlocked) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isBlocked = false;
        this.payments = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "isBlocked", nullable = false, columnDefinition = "boolean default false")
    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
