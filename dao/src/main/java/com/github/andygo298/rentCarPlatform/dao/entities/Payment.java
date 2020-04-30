package com.github.andygo298.rentCarPlatform.dao.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Payment {
    private Long id;
    private Long userId;
    private String cardNum;
    private Date paymentDate;
    private Double paymentValue;
    private User userByUserId;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "card_num", nullable = false, length = 20)
    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @Basic
    @Column(name = "payment_date", nullable = false)
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Basic
    @Column(name = "payment_value", nullable = false, precision = 0)
    public Double getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
                Objects.equals(userId, payment.userId) &&
                Objects.equals(cardNum, payment.cardNum) &&
                Objects.equals(paymentDate, payment.paymentDate) &&
                Objects.equals(paymentValue, payment.paymentValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, cardNum, paymentDate, paymentValue);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }
}
