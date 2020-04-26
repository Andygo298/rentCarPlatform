package com.github.andygo298.rentCarPlatform.model;


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

    public Order() {
        this.orderStatus = OrderStatus.IN_PROGRESS;
    }

    public Long getId() {
        return id;
    }

    public String getPassport() {
        return passport;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getCarId() {
        return carId;
    }

    public Long getUserId() {
        return userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Double getOrderPrice() {
        return orderPrice;
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

        public Order build() {
            return newOrder;
        }
    }
}
