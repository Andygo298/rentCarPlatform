package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.Constant;
import com.github.andygo298.rentCarPlatform.dao.OrderDao;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultCarDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultOrderDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.*;
import com.github.andygo298.rentCarPlatform.service.OrderService;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DefaultOrderService implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(DefaultOrderService.class);
    private OrderDao orderDao = DefaultOrderDao.getInstance();
    private CarDao carDao = DefaultCarDao.getInstance();
    private UserDao userDao = DefaultUserDao.getInstance();

    private static class SingletonHolder {
        static final OrderService HOLDER_INSTANCE = new DefaultOrderService();
    }

    public static OrderService getInstance() {
        return DefaultOrderService.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Double calculateOrderPrice(String startDate, String endDate, Long carId) {
        Integer daysCount = getPeriod(startDate, endDate);
        Car car = carDao.getCarById(carId);
        log.info("calc. Order price {} {} = {}", daysCount, carId, car.getDay_price() * daysCount);
        return car.getDay_price() * daysCount;

    }

    @Override
    public Long saveOrder(Order order) {
        return orderDao.saveOrder(order);
    }

    @Override
    public Integer getOrdersByStatus(OrderStatus status) {
        return orderDao.getOrdersByStatus(status);
    }

    @Override
    public Integer getUserOrdersByStatus(OrderStatus status, Long userId) {
        return orderDao.getUserOrdersByStatus(status, userId);
    }


    @Override
    public List<Order> getUserOrders(Long userId, int page) {
        return orderDao.getOrdersByUserId(userId, ServiceUtil.getSkipRecords(page), Constant.LIMIT_RECORDS);
    }

    @Override
    public List<Order> getOrders(int page) {
        return orderDao.getOrders(ServiceUtil.getSkipRecords(page), Constant.LIMIT_RECORDS);
    }


    @Override
    public int getCountRecordsFromOrders() {
        return orderDao.getCountRecordsFromOrders();
    }

    @Override
    public int getCountRecordsFromOrdersForUser(Long userId) {
        return orderDao.getCountRecordsFromOrdersForUser(userId);
    }

    @Override
    public Double getOrderPriceById(Long orderId) {
        return orderDao.getOrderPriceById(orderId);
    }

    @Override
    public List<OrderInfo> buildOrdersInfo(List<Order> orders) {
        final List<OrderInfo> orderInfoList = new ArrayList<>();
        for (Order order : orders) {
            Car car = carDao.getCarById(order.getCarId());
            User user = userDao.getUserById(order.getUserId());
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
        orderDao.setOrderStatus(orderId, orderStatus);
    }

    @Override
    public Long getCarIdByOrder(Long orderId) {
        return orderDao.getCarIdByOrder(orderId);
    }

    //private methods

    private Integer getPeriod(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        Period period = Period.between(start, end);
        return period.getDays() == 0 ? 1 : period.getDays();
    }

}
