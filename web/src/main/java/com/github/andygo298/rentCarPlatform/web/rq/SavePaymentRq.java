package com.github.andygo298.rentCarPlatform.web.rq;

import com.github.andygo298.rentCarPlatform.model.User;

public class SavePaymentRq {
    private Long orderId;
    private Double orderPrice;
    private String cardNum;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
