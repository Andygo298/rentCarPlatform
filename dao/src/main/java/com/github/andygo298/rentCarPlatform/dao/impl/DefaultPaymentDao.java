package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.PaymentDao;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.converter.PaymentConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.UserConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.PaymentEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.model.Payment;

import org.hibernate.SessionFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPaymentDao implements PaymentDao {
    private final SessionFactory sessionFactory;
    private final UserDao userDao;

    public DefaultPaymentDao(SessionFactory sessionFactory, UserDao userDao) {
        this.sessionFactory = sessionFactory;
        this.userDao = userDao;
    }


    @Override
    public List<Payment> getPayments() {
        TypedQuery<PaymentEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from PaymentEntity", PaymentEntity.class);
        List<PaymentEntity> resultList = query.getResultList();
        return resultList.stream().map(PaymentConverter::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Long savePayment(Payment newPayment) {
        PaymentEntity paymentEntity = PaymentConverter.toEntity(newPayment);
        UserEntity userEntity = UserConverter.toEntity(userDao.getUserById(newPayment.getUserId()));
        paymentEntity.setUserEntity(userEntity);
        sessionFactory.getCurrentSession().save(paymentEntity);
        return paymentEntity.getId();
    }
}