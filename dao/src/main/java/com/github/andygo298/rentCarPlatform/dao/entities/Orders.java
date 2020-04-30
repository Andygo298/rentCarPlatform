package com.github.andygo298.rentCarPlatform.dao.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Orders {
    private Long id;
    private String passport;
    private String phone;
    private Date startDate;
    private Date endDate;
    private Long carsId;
    private Long userId;
    private String status;
    private Double orderPrice;
    private Cars carsByCarsId;
    private User userByUserId;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "passport", nullable = false, length = 255)
    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 255)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "cars_id", nullable = false)
    public Long getCarsId() {
        return carsId;
    }

    public void setCarsId(Long carsId) {
        this.carsId = carsId;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "status", nullable = false, length = 64)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "order_price", nullable = true, precision = 0)
    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(id, orders.id) &&
                Objects.equals(passport, orders.passport) &&
                Objects.equals(phone, orders.phone) &&
                Objects.equals(startDate, orders.startDate) &&
                Objects.equals(endDate, orders.endDate) &&
                Objects.equals(carsId, orders.carsId) &&
                Objects.equals(userId, orders.userId) &&
                Objects.equals(status, orders.status) &&
                Objects.equals(orderPrice, orders.orderPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passport, phone, startDate, endDate, carsId, userId, status, orderPrice);
    }

    @ManyToOne
    @JoinColumn(name = "cars_id", referencedColumnName = "id", nullable = false)
    public Cars getCarsByCarsId() {
        return carsByCarsId;
    }

    public void setCarsByCarsId(Cars carsByCarsId) {
        this.carsByCarsId = carsByCarsId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }
}
