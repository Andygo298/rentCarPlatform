package com.github.andygo298.rentCarPlatform.service.config;

import com.github.andygo298.rentCarPlatform.dao.config.DaoConfig;
import com.github.andygo298.rentCarPlatform.service.*;
import com.github.andygo298.rentCarPlatform.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    private DaoConfig daoConfig;

    public ServiceConfig(DaoConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    @Bean
    public SecurityService securityService() {
        return new DefaultSecurityService(daoConfig.authUserDao());
    }

    @Bean
    public UserService userService() {
        return new DefaultUserService(daoConfig.userDao());
    }

    @Bean
    public CarService carService() {
        return new DefaultCarService(daoConfig.carDao());
    }

    @Bean
    public StaffService staffService() {
        return new DefaultStaffService(daoConfig.staffDao());
    }

    @Bean
    public OrderService orderService() {
        return new DefaultOrderService(daoConfig.orderDao(), daoConfig.carDao(), daoConfig.userDao());
    }
    @Bean
    public PaymentService paymentService(){
        return new DefaultPaymentService(daoConfig.paymentDao());
    }
}
