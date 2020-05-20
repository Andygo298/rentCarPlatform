package com.github.andygo298.rentCarPlatform.dao;


import com.github.andygo298.rentCarPlatform.dao.utils.ConverterDate;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultOrderDao;
import com.github.andygo298.rentCarPlatform.model.*;
import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;
import com.github.andygo298.rentCarPlatform.model.enums.Role;
import org.hibernate.Session;

import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class OrderEntityDaoHiberTest {

    final OrderDao orderDao = DefaultOrderDao.getInstance();

    @BeforeAll
    public static void init() {
        Session session = SFUtil.getSession();
        session.beginTransaction();
        User user1 = new User(null, "TestName1", "TestLastName1", "test1@gmail.com", false);
        User user2 = new User(null, "TestName2", "TestLastName2", "test2@gmail.com", false);
        User user3 = new User(null, "TestName3", "TestLastName3", "test3@gmail.com", false);
        session.saveOrUpdate(user1);
        session.saveOrUpdate(user2);
        session.saveOrUpdate(user3);
        AuthUser authUser1 = new AuthUser(null, "TestLogin1", "TestPass1", Role.USER, user1);
        AuthUser authUser2 = new AuthUser(null, "TestLogin2", "TestPass2", Role.USER, user2);
        AuthUser authUser3 = new AuthUser(null, "TestLogin3", "TestPass3", Role.USER, user3);
        session.saveOrUpdate(authUser1);
        session.saveOrUpdate(authUser2);
        session.saveOrUpdate(authUser3);

        Car car1 = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Arkana")
                .withYear("2019")
                .withType("SUV")
                .withPrice(80)
                .build();
        Car car2 = new Car.CarBuilder(null)
                .withBrand("Opel")
                .withModel("Vectra")
                .withYear("2001")
                .withType("Sedan")
                .withPrice(45)
                .build();

        session.saveOrUpdate(car1);
        session.saveOrUpdate(car2);

        Order order1 = new Order.OrderBuilder(car1.getId(), user1.getId())
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-05-01"), ConverterDate.stringToDate("2020-05-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .withCar(car1)
                .withUser(user1)
                .build();
        Order order2 = new Order.OrderBuilder(car2.getId(), user2.getId())
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-05-01"), ConverterDate.stringToDate("2020-05-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .withCar(car2)
                .withUser(user2)
                .build();

        session.saveOrUpdate(order1);
        session.saveOrUpdate(order2);

        session.getTransaction().commit();
        session.close();

    }

    @Test
    public void saveOrderTest() {
        Session session = SFUtil.getSession();
        session.beginTransaction();
        User user4 = new User(null, "TestName4", "TestLastName4", "test1@gmail.com", false);
        session.saveOrUpdate(user4);
        AuthUser authUser4 = new AuthUser(null, "TestLogin4", "TestPass4", Role.USER, user4);
        session.saveOrUpdate(authUser4);
        Car car3 = new Car.CarBuilder(null)
                .withBrand("Lada")
                .withModel("Granta")
                .withYear("2019")
                .withType("Sedan")
                .withPrice(55)
                .build();
        session.saveOrUpdate(car3);
        session.getTransaction().commit();


        Order order = new Order.OrderBuilder(car3.getId(), user4.getId())
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-03-01"), ConverterDate.stringToDate("2020-03-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .withCar(car3)
                .withUser(user4)
                .build();
        orderDao.saveOrder(order);

        session.beginTransaction();
        Order orderFromDb = session.get(Order.class, order.getId());
        session.close();

        assertNotNull(order);
        assertEquals(order.getId(), orderFromDb.getId());
        assertEquals(order.getPassport(), orderFromDb.getPassport());
        assertEquals(order.getStartDate(), orderFromDb.getStartDate());

    }



    @Test
    public void getOrdersTest() {
        List<Order> orders = orderDao.getOrders(0,10);
        assertNotNull(orders);
        assertEquals(orders.get(0).getPassport(), "MP3334455");
        assertEquals(orders.get(0).getPhone(), "+375298793307");
    }

    @Test
    public void getOrdersByStatusTest() {
        Integer countOrdersFromDb = orderDao.getOrdersByStatus(OrderStatus.IN_PROGRESS);
        Integer exp = 1;
        assertNotNull(countOrdersFromDb);
        assertEquals(exp, countOrdersFromDb);
    }

    @Test
    public void setOrderStatusTest() {
        Long id = 2L;
        orderDao.setOrderStatus(id, OrderStatus.ACCEPTED);
        Session session = SFUtil.getSession();
        session.beginTransaction();
        Order orderFromDb = session.get(Order.class, id);
        session.getTransaction().commit();
        session.close();
        assertNotNull(orderFromDb);
        assertEquals(orderFromDb.getOrderStatus().name(), OrderStatus.ACCEPTED.name());


    }

    @Test
    public void getUserOrdersByStatusTest() {
        Integer countCurrentUserOrders = orderDao.getUserOrdersByStatus(OrderStatus.IN_PROGRESS, 2L);
        Integer exp = 0;
        assertNotNull(countCurrentUserOrders);
        assertEquals(exp, countCurrentUserOrders);
    }

    @Test
    public void getCountRecordsFromOrders(){
        int countRecordsFromDb = orderDao.getCountRecordsFromOrders();
        int exp = 2;
        assertEquals(exp,countRecordsFromDb);
    }

    @Test
    public void getCountRecordsFromOrdersForUserTest(){
        long id = 2L;
        int countRecordsFromDb = orderDao.getCountRecordsFromOrdersForUser(id);
        int exp = 0;
        assertEquals(exp,countRecordsFromDb);
    }


    @AfterAll
    public static void afterAll() {
        Session session = SFUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete from OrderEntity o where o.id=:id").setParameter("id", 1L).executeUpdate();
        session.createQuery("delete from OrderEntity o where o.id=:id").setParameter("id", 2L).executeUpdate();
        session.createQuery("delete from OrderEntity o where o.id=:id").setParameter("id", 3L).executeUpdate();
        session.createQuery("delete from AuthUserEntity au where au.id=:id").setParameter("id", 1L).executeUpdate();
        session.createQuery("delete from AuthUserEntity au where au.id=:id").setParameter("id", 2L).executeUpdate();
        session.createQuery("delete from AuthUserEntity au where au.id=:id").setParameter("id", 3L).executeUpdate();
        session.createQuery("delete from AuthUserEntity au where au.id=:id").setParameter("id", 4L).executeUpdate();
        session.createQuery("delete from UserEntity u where u.id=:id").setParameter("id", 1L).executeUpdate();
        session.createQuery("delete from UserEntity u where u.id=:id").setParameter("id", 2L).executeUpdate();
        session.createQuery("delete from UserEntity u where u.id=:id").setParameter("id", 3L).executeUpdate();
        session.createQuery("delete from UserEntity u where u.id=:id").setParameter("id", 4L).executeUpdate();
        session.createQuery("delete from CarEntity c where c.id=:id").setParameter("id", 1L).executeUpdate();
        session.createQuery("delete from CarEntity c where c.id=:id").setParameter("id", 2L).executeUpdate();
        session.createQuery("delete from CarEntity c where c.id=:id").setParameter("id", 3L).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }
}
