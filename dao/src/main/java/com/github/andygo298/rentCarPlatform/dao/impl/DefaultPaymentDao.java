package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.PaymentDao;
import com.github.andygo298.rentCarPlatform.dao.utils.SFUtil;
import com.github.andygo298.rentCarPlatform.model.Payment;

import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

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
            TypedQuery<Payment> query = session.createQuery("from Payment", Payment.class);
            List<Payment> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList;
        }
    }

    @Override
    public Long savePayment(Payment newPayment) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.save(newPayment);
            Long id = newPayment.getId();
            session.getTransaction().commit();
            session.close();
            return id;
        }

    }
}