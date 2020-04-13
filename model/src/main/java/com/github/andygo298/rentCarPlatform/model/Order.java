package com.github.andygo298.rentCarPlatform.model;

public class Order {
    private Long id;
    private String passport;
    private String phone;
    private String startDate;
    private String endDate;
    private Long carId;
    private Long userId;
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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
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

        public Order.OrderBuilder withDates(String startDate, String endDate) {
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
