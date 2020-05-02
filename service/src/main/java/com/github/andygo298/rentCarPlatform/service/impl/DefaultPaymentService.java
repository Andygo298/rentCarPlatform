package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.DefaultPaymentDao;
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

    @Override
    public List<Payment> getPayments() {
        return DefaultPaymentDao.getInstance().getPayments();
    }

    @Override
    public Long savePayment(Payment newPayment) {
        return DefaultPaymentDao.getInstance().savePayment(newPayment);
    }
}
