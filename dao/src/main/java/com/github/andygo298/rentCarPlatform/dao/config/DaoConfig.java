package com.github.andygo298.rentCarPlatform.dao.config;

import com.github.andygo298.rentCarPlatform.dao.*;
import com.github.andygo298.rentCarPlatform.dao.impl.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(HibernateConfig.class)
@EnableTransactionManagement
public class DaoConfig {

    private final SessionFactory sessionFactory;

    public DaoConfig(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Bean
    public AuthUserDao authUserDao() {
        return new DefaultAuthUserDao(sessionFactory, userDao());
    }

    @Bean
    public CarDao carDao() {
        return new DefaultCarDao(sessionFactory);
    }

    @Bean
    public OrderDao orderDao() {
        return new DefaultOrderDao(sessionFactory, userDao(), carDao());
    }

    @Bean
    public PaymentDao paymentDao() {
        return new DefaultPaymentDao(sessionFactory, userDao());
    }

    @Bean
    public StaffDao staffDao() {
        return new DefaultStaffDao(sessionFactory);
    }

    @Bean
    public UserDao userDao() {
        return new DefaultUserDao(sessionFactory);
    }
}
