package com.github.andygo298.rentCarPlatform.model;

import java.time.LocalDate;

public class Payment {
    Long id;
    Long userId;//fk_user
    String cardNum;
    LocalDate paymentDate;
    Double paymentValue;


    public Payment() {
        this.paymentDate = LocalDate.now();
    }

//from converter
    public Payment(Long id, Long userId, String cardNum, LocalDate paymentDate, Double paymentValue) {
        this.id = id;
        this.userId = userId;
        this.cardNum = cardNum;
        this.paymentDate = paymentDate;
        this.paymentValue = paymentValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
    }

    //builder class
    public static class PaymentBuilder {
        private Payment newPayment;

        public PaymentBuilder() {
            newPayment = new Payment();
        }


        public Payment.PaymentBuilder withCardNum(String cardNum) {
            newPayment.cardNum = cardNum;
            return this;
        }


        public Payment.PaymentBuilder withPaymentValue(Double paymentValue) {
            newPayment.paymentValue = paymentValue;
            return this;
        }
        public Payment.PaymentBuilder withUserId(Long userId) {
            newPayment.userId = userId;
            return this;
        }


        public Payment build() {
            return newPayment;
        }
    }
}
