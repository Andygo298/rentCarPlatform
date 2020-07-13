package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.PaymentDao;
import com.github.andygo298.rentCarPlatform.model.Payment;
import com.github.andygo298.rentCarPlatform.service.PaymentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultPaymentService implements PaymentService {
    private final PaymentDao paymentDao;

    public DefaultPaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    @Transactional
    public List<Payment> getPayments() {
        return paymentDao.getPayments();
    }

    @Override
    @Transactional
    public Long savePayment(Payment newPayment) {
        return paymentDao.savePayment(newPayment);
    }
}
