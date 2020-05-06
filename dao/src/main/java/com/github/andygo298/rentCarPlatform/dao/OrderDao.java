package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.OrderStatus;

import java.util.List;

public interface OrderDao {
    Long saveOrder(Order order);
    Integer getOrdersByStatus(OrderStatus status);
    Integer getUserOrdersByStatus(OrderStatus status, Long userId);
    List<Order> getOrdersByUserId(Long userId, int skipRecords, int limitRecords);
    List<Order> getOrders(int skipRecords, int limitRecords);
    int getCountRecordsFromOrders();
    int getCountRecordsFromOrdersForUser(Long userId);
    Double getOrderPriceById(Long orderId);
    void setOrderStatus(Long orderId, OrderStatus orderStatus);
    Long getCarIdByOrder(Long orderId);
}
