package com.github.andygo298.rentCarPlatform.model;

public class OrderInfo {
    private String userName;
    private String carName;
    private String passport; // TODO: UI for admin only
    private String phone;
    private String startDate;
    private String endDate;
    private OrderStatus orderStatus;
    private Double orderPrice;
    private Long orderId;

    public OrderInfo(Long orderId) {
        this.orderId = orderId;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }

    public String getCarName() {
        return carName;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public static class OrderInfoBuilder {
        private OrderInfo newOrderInfo;

        public OrderInfoBuilder(Long orderId) {
            newOrderInfo = new OrderInfo(orderId);
        }

        public OrderInfo.OrderInfoBuilder withUserInfo(String firstName, String lastName) {
            newOrderInfo.userName = firstName + " " + lastName;
            return this;
        }

        public OrderInfo.OrderInfoBuilder withCarInfo(String model, String brand) {
            newOrderInfo.carName =  model + " " + brand;
            return this;
        }

        public OrderInfo.OrderInfoBuilder withDates(String startDate, String endDate) {
            newOrderInfo.startDate = startDate;
            newOrderInfo.endDate = endDate;
            return this;
        }

        public OrderInfo.OrderInfoBuilder withOrderInfo(Double price, String passport, String phone) {
            newOrderInfo.orderPrice = price;
            newOrderInfo.passport = passport;
            newOrderInfo.phone = phone;
            return this;
        }

        public OrderInfo.OrderInfoBuilder withStatus(OrderStatus status) {
            newOrderInfo.orderStatus = status;
            return this;
        }

        public OrderInfo build() {
            return newOrderInfo;
        }
    }
}
