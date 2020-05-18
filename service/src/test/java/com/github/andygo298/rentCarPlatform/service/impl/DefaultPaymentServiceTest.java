package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.PaymentDao;

import com.github.andygo298.rentCarPlatform.model.Payment;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DefaultPaymentServiceTest {

    @Mock
    PaymentDao paymentDao;

    @InjectMocks
    DefaultPaymentService paymentService;

    @Test
    void getInstanceTest() {
        PaymentService instance = DefaultPaymentService.getInstance();
        assertNotNull(instance);
    }

    @Test
    void savePaymentTest(){
        User mockUser = new User(1L,"Ivan","Ivanov","iva@gmail.com",false);
        Payment mockPayment = new Payment.PaymentBuilder()
                .withCardNum("1111 2222 3333 4444")
                .withPaymentValue(1500.0)
                .withUser(mockUser)
                .build();
        Long expectedResult = 1L;
        when(paymentDao.savePayment(mockPayment)).thenReturn(expectedResult);

        Long idFromDb = paymentService.savePayment(mockPayment);
        assertEquals(expectedResult, idFromDb);

    }

    @Test
    void getPaymentsTest(){
        User mockUser1 = new User(1L,"Petr","Petrov","petr@gmail.com",false);
        User mockUser2 = new User(2L,"Petr","Petrov","petr@gmail.com",false);
        Payment mockPayment1 = new Payment.PaymentBuilder()
                .withCardNum("1111 2222 3333 4444")
                .withPaymentValue(1500.0)
                .withUser(mockUser1).build();
        Payment mockPayment2 = new Payment.PaymentBuilder()
                .withCardNum("1111 2222 3333 4444")
                .withPaymentValue(1500.0)
                .withUser(mockUser2).build();

        List<Payment> payments = Arrays.asList(mockPayment1, mockPayment2);
        when(paymentDao.getPayments()).thenReturn(payments);

        List<Payment> paymentsFromDb = paymentService.getPayments();
        assertEquals(payments,paymentsFromDb);
    }
}
