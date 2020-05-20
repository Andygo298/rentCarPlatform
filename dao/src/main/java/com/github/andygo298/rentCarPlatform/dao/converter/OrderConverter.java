package com.github.andygo298.rentCarPlatform.dao.converter;

import com.github.andygo298.rentCarPlatform.dao.entity.OrderEntity;
import com.github.andygo298.rentCarPlatform.model.Order;

public class OrderConverter {

    public static OrderEntity toEntity(Order order){
        if (order == null){
            return null;
        }
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setPassport(order.getPassport());
        orderEntity.setPhone(order.getPhone());
        orderEntity.setStartDate(order.getStartDate());
        orderEntity.setEndDate(order.getEndDate());
        orderEntity.setUserId(order.getUserId());
        orderEntity.setCarId(order.getCarId());
        orderEntity.setOrderStatus(order.getOrderStatus());
        orderEntity.setOrderPrice(order.getOrderPrice());
        orderEntity.setUserEntity(UserConverter.toEntity(order.getUser()));
        orderEntity.setCarEntity(CarConverter.toEntity(order.getCar()));
        return orderEntity;
    }

    public static Order fromEntity(OrderEntity orderEntity){
        if (orderEntity == null){
            return null;
        }
        return new Order(
               orderEntity.getId(),
               orderEntity.getPassport(),
               orderEntity.getPhone(),
               orderEntity.getStartDate(),
               orderEntity.getEndDate(),
               orderEntity.getUserId(),
               orderEntity.getCarId(),
               orderEntity.getOrderStatus(),
               orderEntity.getOrderPrice(),
               UserConverter.fromEntity(orderEntity.getUserEntity()),
               CarConverter.fromEntity(orderEntity.getCarEntity())
        );
    }
}
