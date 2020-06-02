package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.PaymentDao;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.dao.converter.PaymentConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.UserConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.PaymentEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.model.Payment;

import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPaymentDao implements PaymentDao {

    private static class SingletonHolder {
        static final PaymentDao HOLDER_INSTANCE = new DefaultPaymentDao();
    }
    public static PaymentDao getInstance() {
        return DefaultPaymentDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Payment> getPayments() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<PaymentEntity> query = session.createQuery("from PaymentEntity", PaymentEntity.class);
            List<PaymentEntity> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList.stream().map(PaymentConverter::fromEntity).collect(Collectors.toList());
        }
    }

    @Override
    public Long savePayment(Payment newPayment) {
        PaymentEntity paymentEntity = PaymentConverter.toEntity(newPayment);
        UserEntity userEntity = UserConverter.toEntity(DefaultUserDao.getInstance().getUserById(newPayment.getUserId()));
        paymentEntity.setUserEntity(userEntity);
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.save(paymentEntity);
            Long id = paymentEntity.getId();
            session.getTransaction().commit();
            session.close();
            return id;
        }

    }
}