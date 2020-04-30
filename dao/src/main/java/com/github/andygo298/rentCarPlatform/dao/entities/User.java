package com.github.andygo298.rentCarPlatform.dao.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isBlocked;
    private List<AuthUser> authUsersById;
    private List<Orders> ordersById;
    private List<Payment> paymentsById;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "isBlocked", nullable = true)
    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(isBlocked, user.isBlocked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, isBlocked);
    }

    @OneToMany(mappedBy = "userByUserId")
    public List<AuthUser> getAuthUsersById() {
        return authUsersById;
    }

    public void setAuthUsersById(List<AuthUser> authUsersById) {
        this.authUsersById = authUsersById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public List<Orders> getOrdersById() {
        return ordersById;
    }

    public void setOrdersById(List<Orders> ordersById) {
        this.ordersById = ordersById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public List<Payment> getPaymentsById() {
        return paymentsById;
    }

    public void setPaymentsById(List<Payment> paymentsById) {
        this.paymentsById = paymentsById;
    }
}
