package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.Payment;

import java.util.List;

public interface PaymentDao {
    List<Payment> getPayments();
    Long savePayment(Payment newPayment);
}
