package com.github.andygo298.rentCarPlatform.service;

import com.github.andygo298.rentCarPlatform.model.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getPayments();
    Long savePayment(Payment newPayment);
}
