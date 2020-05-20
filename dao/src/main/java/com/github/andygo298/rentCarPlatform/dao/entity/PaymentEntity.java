package com.github.andygo298.rentCarPlatform.dao.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
public class PaymentEntity {
    Long id;
    Long userId;//fk_user
    String cardNum;
    LocalDate paymentDate;
    Double paymentValue;
    UserEntity userEntity;


    public PaymentEntity() {
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
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public static class PaymentBuilder {
        private PaymentEntity newPaymentEntity;

        public PaymentBuilder() {
            newPaymentEntity = new PaymentEntity();
        }


        public PaymentEntity.PaymentBuilder withCardNum(String cardNum) {
            newPaymentEntity.cardNum = cardNum;
            return this;
        }


        public PaymentEntity.PaymentBuilder withPaymentValue(Double paymentValue) {
            newPaymentEntity.paymentValue = paymentValue;
            return this;
        }

        public PaymentEntity.PaymentBuilder withUser(UserEntity userEntity) {
            newPaymentEntity.userEntity = userEntity;
            return this;
        }

        public PaymentEntity build() {
            return newPaymentEntity;
        }
    }
}
