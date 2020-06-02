package com.github.andygo298.rentCarPlatform.dao.converter;

import com.github.andygo298.rentCarPlatform.dao.entity.PaymentEntity;
import com.github.andygo298.rentCarPlatform.model.Payment;

public class PaymentConverter {
    public static Payment fromEntity(PaymentEntity paymentEntity) {
        if (paymentEntity == null) {
            return null;
        }
        return new Payment(
                paymentEntity.getId(),
                paymentEntity.getUserId(),
                paymentEntity.getCardNum(),
                paymentEntity.getPaymentDate(),
                paymentEntity.getPaymentValue()
                );
    }

    public static PaymentEntity toEntity(Payment payment) {
        if (payment == null) {
            return null;
        }
        final PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(payment.getId());
        paymentEntity.setUserId(payment.getUserId());
        paymentEntity.setCardNum(payment.getCardNum());
        paymentEntity.setPaymentDate(payment.getPaymentDate());
        paymentEntity.setPaymentValue(payment.getPaymentValue());
        return paymentEntity;
    }
    }
