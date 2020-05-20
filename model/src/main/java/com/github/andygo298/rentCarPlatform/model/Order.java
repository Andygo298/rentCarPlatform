package com.github.andygo298.rentCarPlatform.model;


import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDate;

public class Order {
    private Long id;
    private String passport;
    private String phone;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long carId;//fk_car
    private Long userId;//fk_user
    private OrderStatus orderStatus;
    private Double orderPrice;

    private User user;
    private Car car;

    public Order() {
        this.orderStatus = OrderStatus.IN_PROGRESS;
    }

//from converter
    public Order(Long id, String passport, String phone, LocalDate startDate, LocalDate endDate, Long carId, Long userId, OrderStatus orderStatus, Double orderPrice, User user, Car car) {
        this.id = id;
        this.passport = passport;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.carId = carId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.orderPrice = orderPrice;
        this.user = user;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public static class OrderBuilder {
        private Order newOrder;

        public OrderBuilder(Long carId, Long userId) {
            newOrder = new Order();
            newOrder.carId = carId;
            newOrder.userId = userId;
        }

        public Order.OrderBuilder withPassport(String passport) {
            newOrder.passport = passport;
            return this;
        }

        public Order.OrderBuilder withTelephone(String phone) {
            newOrder.phone = phone;
            return this;
        }

        public Order.OrderBuilder withDates(LocalDate startDate, LocalDate endDate) {
            newOrder.startDate = startDate;
            newOrder.endDate = endDate;
            return this;
        }

        public Order.OrderBuilder withPrice(Double price) {
            newOrder.orderPrice = price;
            return this;
        }
        public Order.OrderBuilder withUser(User user) {
            newOrder.user = user;
            return this;
        }
        public Order.OrderBuilder withCar(Car car) {
            newOrder.car = car;
            return this;
        }

        public Order build() {
            return newOrder;
        }
    }

}
