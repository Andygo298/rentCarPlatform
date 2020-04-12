package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.OrderStatus;

import java.util.List;

public interface OrderDao {
    Long saveOrder(Order order);
    Integer getOrdersByStatus(OrderStatus status);
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getOrders();
    Double getOrderPriceById(Long orderId);
    void setOrderStatus(Long orderId, OrderStatus orderStatus);
    Long getCarIdByOrder(Long orderId);
}
