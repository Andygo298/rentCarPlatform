package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.PaymentDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultPaymentDao;
import com.github.andygo298.rentCarPlatform.model.Payment;
import com.github.andygo298.rentCarPlatform.service.PaymentService;

import java.util.List;

public class DefaultPaymentService implements PaymentService {

    private static class SingletonHolder {
        static final PaymentService HOLDER_INSTANCE = new DefaultPaymentService();
    }

    public static PaymentService getInstance() {
        return DefaultPaymentService.SingletonHolder.HOLDER_INSTANCE;
    }

    private PaymentDao paymentDao = DefaultPaymentDao.getInstance();

    @Override
    public List<Payment> getPayments() {
        return paymentDao.getPayments();
    }

    @Override
    public Long savePayment(Payment newPayment) {
        return paymentDao.savePayment(newPayment);
    }
}
