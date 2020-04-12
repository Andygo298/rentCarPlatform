package com.github.andygo298.rentCarPlatform.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Payment {
    Long id;
    Long userId;
    String cardNum;
    String paymentDate;
    Double paymentValue;

    public Payment() {
        this.paymentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public Double getPaymentValue() {
        return paymentValue;
    }

    public static class PaymentBuilder {
        private Payment newPayment;

        public PaymentBuilder(Long userId) {
            newPayment = new Payment();
            newPayment.userId = userId;
        }

        public Payment.PaymentBuilder withId(Long id) {
            newPayment.id = id;
            return this;
        }

        public Payment.PaymentBuilder withCardNum(String cardNum) {
            newPayment.cardNum = cardNum;
            return this;
        }


        public Payment.PaymentBuilder withPaymentValue(Double paymentValue) {
            newPayment.paymentValue = paymentValue;
            return this;
        }

        public Payment build() {
            return newPayment;
        }
    }
}
