package com.github.andygo298.rentCarPlatform.dao;


import com.github.andygo298.rentCarPlatform.model.Payment;
import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.Session;

import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentsDaoHiberTest {

    final PaymentDao paymentDao = DefaultPaymentDao.getInstance();
    final UserDao userDao = DefaultUserDao.getInstance();


    @Test
    public void savePaymentTest() {
        User user = new User(null,"Andrew","Lozouski","andygo298@gmail.com",false);
        long saveId = userDao.save(user);
        User userActual = userDao.getUserById(saveId);
        Payment paymentToSave = new Payment.PaymentBuilder(saveId)
                .withCardNum("2200443311225544")
                .withPaymentValue(1000.0)
                .withUser(userActual)
                .build();

        paymentDao.savePayment(paymentToSave);
        Session session = SFUtil.getSession();
        session.beginTransaction();
        Payment paymentFromDb = session.get(Payment.class, paymentToSave.getId());
        assertEquals(paymentToSave.getId(), paymentFromDb.getId());
        assertEquals(paymentToSave.getCardNum(), paymentFromDb.getCardNum());
        session.getTransaction().commit();
        session.close();
    }
    @Test
    public void getPaymentsListTest(){
        User user = new User(null,"Andrew","Lozouski","andygo298@gmail.com",false);
        long saveId = userDao.save(user);
        User userActual = userDao.getUserById(saveId);
        Payment paymentToSave = new Payment.PaymentBuilder(saveId)
                .withCardNum("2200443311225544")
                .withPaymentValue(1000.0)
                .withUser(userActual)
                .build();
        paymentDao.savePayment(paymentToSave);

        Session session = SFUtil.getSession();
        session.beginTransaction();
        TypedQuery<Payment> query = session.createQuery("from Payment ", Payment.class);
        List<Payment> resultList = query.getResultList();
        List<Payment> paymentsFromDb = paymentDao.getPayments();

        assertEquals(resultList.size(),paymentsFromDb.size());
        assertEquals(paymentsFromDb.get(0).getCardNum(), resultList.get(0).getCardNum());

        session.close();
    }

}
