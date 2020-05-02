package com.github.andygo298.rentCarPlatform.model;


import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "orders")
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    @Basic
    @Column(name = "end_date", nullable = false)
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    @Basic
    @Column(name = "cars_id", nullable = false, insertable = false, updatable = false)
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
    @Basic
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 64)
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    @Basic
    @Column(name = "order_price", nullable = true, precision = 0)
    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @ManyToOne
    @JoinColumn(name = "cars_id", referencedColumnName = "id", nullable = false)
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

        public Order.OrderBuilder withId(Long id) {
            newOrder.id = id;
            return this;
        }

        public Order.OrderBuilder withStatus(OrderStatus status) {
            newOrder.orderStatus = status;
            return this;
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
