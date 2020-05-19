package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.utils.ConverterDate;
import com.github.andygo298.rentCarPlatform.dao.OrderDao;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.OrderService;
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
class DefaultOrderServiceTest {

    @Mock
    OrderDao orderDao;


    @InjectMocks
    DefaultOrderService defaultOrderService;


    @Test
    void getInstance() {
        OrderService instance = DefaultOrderService.getInstance();
        assertNotNull(instance);
    }

    @Test
    void calculateOrderPrice() {
    }

    @Test
    void saveOrder() {
        User user1 = new User(null, "TestName1", "TestLastName1", "test1@gmail.com", false);
        Car car1 = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Arkana")
                .withYear("2019")
                .withType("SUV")
                .withPrice(80)
                .build();
        Order order1 = new Order.OrderBuilder(car1.getId(), user1.getId())
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-05-01"), ConverterDate.stringToDate("2020-05-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .withCar(car1)
                .withUser(user1)
                .build();
        Long expId = 4L;
        when(orderDao.saveOrder(order1)).thenReturn(expId);
        Long actLongId = defaultOrderService.saveOrder(order1);
        assertNotNull(actLongId);
        assertEquals(expId,actLongId);
    }

    @Test
    void getOrdersByStatus() {
        Integer exp = 4;
        when(orderDao.getOrdersByStatus(OrderStatus.IN_PROGRESS)).thenReturn(4);
        Integer ordersByStatus = defaultOrderService.getOrdersByStatus(OrderStatus.IN_PROGRESS);
        assertNotNull(ordersByStatus);
        assertEquals(exp,ordersByStatus);
    }

    @Test
    void getUserOrdersByStatus() {
        Long userId = 10L;
        Integer exp = 2;
        when(orderDao.getUserOrdersByStatus(OrderStatus.COMPLETED,userId)).thenReturn(2);
        Integer userOrdersByStatus = defaultOrderService.getUserOrdersByStatus(OrderStatus.COMPLETED, userId);
        assertNotNull(userOrdersByStatus);
        assertEquals(exp,userOrdersByStatus);
    }

    @Test
    void getUserOrders() {
        User user1 = new User(null, "TestName1", "TestLastName1", "test1@gmail.com", false);
        Car car1 = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Arkana")
                .withYear("2019")
                .withType("SUV")
                .withPrice(80)
                .build();
        Car car2 = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Duster")
                .withYear("2019")
                .withType("SUV")
                .withPrice(60)
                .build();
        Order order1 = new Order.OrderBuilder(car1.getId(), user1.getId())
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-05-01"), ConverterDate.stringToDate("2020-05-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .withCar(car1)
                .withUser(user1)
                .build();
        Order order2 = new Order.OrderBuilder(car1.getId(), user1.getId())
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-05-01"), ConverterDate.stringToDate("2020-05-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .withCar(car2)
                .withUser(user1)
                .build();
        List<Order> orderList = Arrays.asList(order1, order2);
        Long userId = 3L;
        when(orderDao.getOrdersByUserId(userId,0,3)).thenReturn(orderList);
        List<Order> userOrders = defaultOrderService.getUserOrders(userId, 1);
        assertNotNull(userOrders);
        assertEquals(orderList,userOrders);
    }

    @Test
    void getOrders() {
        User user1 = new User(null, "TestName1", "TestLastName1", "test1@gmail.com", false);
        Car car1 = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Arkana")
                .withYear("2019")
                .withType("SUV")
                .withPrice(80)
                .build();
        Car car2 = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Duster")
                .withYear("2019")
                .withType("SUV")
                .withPrice(60)
                .build();
        Order order1 = new Order.OrderBuilder(car1.getId(), user1.getId())
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-05-01"), ConverterDate.stringToDate("2020-05-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .withCar(car1)
                .withUser(user1)
                .build();
        Order order2 = new Order.OrderBuilder(car1.getId(), user1.getId())
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-05-01"), ConverterDate.stringToDate("2020-05-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .withCar(car2)
                .withUser(user1)
                .build();
        List<Order> orderList = Arrays.asList(order1, order2);
        when(orderDao.getOrders(0, 3)).thenReturn(orderList);
        List<Order> ordersActual = defaultOrderService.getOrders(1);
        assertNotNull(ordersActual);
        assertEquals(orderList, ordersActual);
    }

    @Test
    void getCountRecordsFromOrders() {
        int countExp = 2;
        when(orderDao.getCountRecordsFromOrders()).thenReturn(2);
        int countRecordsFromOrders = defaultOrderService.getCountRecordsFromOrders();
        assertEquals(countExp, countRecordsFromOrders);

    }

    @Test
    void getCountRecordsFromOrdersForUser() {
        int countExp = 5;
        Long userId = 3L;
        when(orderDao.getCountRecordsFromOrdersForUser(userId)).thenReturn(5);
        int countRecordsFromOrdersForUser = defaultOrderService.getCountRecordsFromOrdersForUser(userId);
        assertEquals(countExp, countRecordsFromOrdersForUser);
    }

    @Test
    void getOrderPriceById() {
        Double ordPrice = 590D;
        when(orderDao.getOrderPriceById(2L)).thenReturn(590D);
        Double orderPriceById = defaultOrderService.getOrderPriceById(2L);
        assertNotNull(orderPriceById);
        assertEquals(ordPrice,orderPriceById);
    }
    @Test
    void setOrderStatusTest(){
        Long id = 4L;
        doNothing().when(orderDao).setOrderStatus(anyLong(),any());
        defaultOrderService.setOrderStatus(id,OrderStatus.ACCEPTED);
        verify(orderDao, times(1)).setOrderStatus(id,OrderStatus.ACCEPTED);
    }
    @Test
    void getCarIdByOrderTest(){
        Long orderId = 1L;
        Long carId = 6L;
        when(orderDao.getCarIdByOrder(anyLong())).thenReturn(carId);
        Long carIdByOrder = defaultOrderService.getCarIdByOrder(orderId);
        assertNotNull(carIdByOrder);
        assertEquals(carId,carIdByOrder);
    }
}