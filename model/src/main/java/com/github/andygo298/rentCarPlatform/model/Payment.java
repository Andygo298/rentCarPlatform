package com.github.andygo298.rentCarPlatform.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
public class Payment {
    Long id;
    Long userId;//fk_user
    String cardNum;
    LocalDate paymentDate;
    Double paymentValue;
    User user;


    public Payment() {
        this.paymentDate = LocalDate.now();
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

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "card_num", nullable = false, length = 20)
    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @Column(name = "payment_date", nullable = false)
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Column(name = "payment_value", nullable = false)
    public Double getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
    }
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

        public Payment.PaymentBuilder withUser(User user) {
            newPayment.user = user;
            return this;
        }

        public Payment build() {
            return newPayment;
        }
    }
}
