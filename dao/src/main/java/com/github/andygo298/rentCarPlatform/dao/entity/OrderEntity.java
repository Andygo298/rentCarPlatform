package com.github.andygo298.rentCarPlatform.dao.entity;


import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class OrderEntity {

    private Long id;
    private String passport;
    private String phone;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long carId;//fk_car
    private Long userId;//fk_user
    private OrderStatus orderStatus;
    private Double orderPrice;

    private UserEntity userEntity;
    private CarEntity carEntity;

    public OrderEntity() {
        this.orderStatus = OrderStatus.IN_PROGRESS;
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

    @Column(name = "passport")
    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date", nullable = false)
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Column(name = "cars_id", nullable = false, insertable = false, updatable = false)
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 64)
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "order_price")
    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    @ManyToOne
    @JoinColumn(name = "cars_id", referencedColumnName = "car_id", nullable = false)
    public CarEntity getCarEntity() {
        return carEntity;
    }

    public void setCarEntity(CarEntity carEntity) {
        this.carEntity = carEntity;
    }

    public static class OrderBuilder {
        private OrderEntity newOrderEntity;

        public OrderBuilder(Long carId, Long userId) {
            newOrderEntity = new OrderEntity();
            newOrderEntity.carId = carId;
            newOrderEntity.userId = userId;
        }

        public OrderEntity.OrderBuilder withPassport(String passport) {
            newOrderEntity.passport = passport;
            return this;
        }

        public OrderEntity.OrderBuilder withTelephone(String phone) {
            newOrderEntity.phone = phone;
            return this;
        }

        public OrderEntity.OrderBuilder withDates(LocalDate startDate, LocalDate endDate) {
            newOrderEntity.startDate = startDate;
            newOrderEntity.endDate = endDate;
            return this;
        }

        public OrderEntity.OrderBuilder withPrice(Double price) {
            newOrderEntity.orderPrice = price;
            return this;
        }
        public OrderEntity.OrderBuilder withUser(UserEntity userEntity) {
            newOrderEntity.userEntity = userEntity;
            return this;
        }
        public OrderEntity.OrderBuilder withCar(CarEntity carEntity) {
            newOrderEntity.carEntity = carEntity;
            return this;
        }

        public OrderEntity build() {
            return newOrderEntity;
        }
    }

}
