package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.impl.DefaultCarDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultOrderDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.*;
import com.github.andygo298.rentCarPlatform.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DefaultOrderService implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(DefaultOrderService.class);

    private static class SingletonHolder {
        static final OrderService HOLDER_INSTANCE = new DefaultOrderService();
    }

    public static OrderService getInstance() {
        return DefaultOrderService.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Double calculateOrderPrice(String startDate, String endDate, Long carId) {
        Integer daysCount = getPeriod(startDate, endDate);
        return calculateOrderPrice(daysCount, carId);
    }

    @Override
    public Long saveOrder(Order order) {
        Long orderId = DefaultOrderDao.getInstance().saveOrder(order);
        if (orderId != null) {
            DefaultCarDao.getInstance().changeRentStatus(order.getCarId(), true);
        }
        return orderId;
    }

    @Override
    public Integer getOrdersByStatus(OrderStatus status) {
        return DefaultOrderDao.getInstance().getOrdersByStatus(status);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return DefaultOrderDao.getInstance().getOrdersByUserId(userId);
    }

    @Override
    public List<Order> getOrders() {
        return DefaultOrderDao.getInstance().getOrders();
    }

    @Override
    public Double getOrderPriceById(Long orderId) {
        return DefaultOrderDao.getInstance().getOrderPriceById(orderId);
    }

    @Override
    public List<OrderInfo> buildOrdersInfo(List<Order> orders) {
        final List<OrderInfo> orderInfoList = new ArrayList<>();
        for (Order order : orders) {
            Car car = DefaultCarDao.getInstance().getCarById(order.getCarId());
            User user = DefaultUserDao.getInstance().getUserById(order.getUserId());
            OrderInfo info = new OrderInfo.OrderInfoBuilder(order.getId())
                    .withCarInfo(car.getModel(), car.getBrand())
                    .withUserInfo(user.getFirstName(), user.getLastName())
                    .withOrderInfo(order.getOrderPrice(), order.getPassport(), order.getPhone())
                    .withDates(order.getStartDate(), order.getEndDate())
                    .withStatus(order.getOrderStatus())
                    .build();
            orderInfoList.add(info);
        }
        return orderInfoList;
    }

    @Override
    public void setOrderStatus(Long orderId, OrderStatus orderStatus) {
        DefaultOrderDao.getInstance().setOrderStatus(orderId, orderStatus);
    }

    @Override
    public Long getCarIdByOrder(Long orderId) {
        return DefaultOrderDao.getInstance().getCarIdByOrder(orderId);
    }

    private Integer getPeriod(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        Period period = Period.between(start, end);
        return period.getDays();
    }

    private Double calculateOrderPrice(Integer days, Long carId) {
        Car car = DefaultCarDao.getInstance().getCarById(carId);
        log.info("calc. Order price {} {} = {}", days, carId, car.getDay_price() * days);
        return car.getDay_price() * days;
    }
}
