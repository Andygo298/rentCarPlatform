package com.github.andygo298.rentCarPlatform.service;

import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.OrderInfo;
import com.github.andygo298.rentCarPlatform.model.OrderStatus;

import java.util.List;

public interface OrderService {
    Double calculateOrderPrice(String startDate, String endDate, Long carId);
    Long saveOrder(Order order);
    Integer getOrdersByStatus(OrderStatus status);
    List<Order> getUserOrders(Long userId, int page);
    List<Order> getOrders(int page);
    int getCountRecordsFromOrders();
    int getCountRecordsFromOrdersForUser(Long userId);
    Double getOrderPriceById(Long orderId);
    List<OrderInfo> buildOrdersInfo(List<Order> orders);
    void setOrderStatus(Long orderId, OrderStatus orderStatus);
    Long getCarIdByOrder(Long orderId);
    Integer getUserOrdersByStatus(OrderStatus status, Long userId);

}
