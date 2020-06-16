package Impl;


import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.OrderDao;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.config.DaoConfig;
import com.github.andygo298.rentCarPlatform.dao.entity.OrderEntity;
import com.github.andygo298.rentCarPlatform.dao.utils.ConverterDate;
import com.github.andygo298.rentCarPlatform.model.*;
import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;
import com.github.andygo298.rentCarPlatform.model.enums.Role;
import org.hibernate.Session;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class OrderEntityDaoHiberTest {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CarDao carDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionFactory sessionFactory;
/*
    @BeforeAll
    public static void init() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user1 = new User(null, "TestName1", "TestLastName1", "test1@gmail.com", false);
        User user2 = new User(null, "TestName2", "TestLastName2", "test2@gmail.com", false);
        User user3 = new User(null, "TestName3", "TestLastName3", "test3@gmail.com", false);
        session.saveOrUpdate(user1);
        session.saveOrUpdate(user2);
        session.saveOrUpdate(user3);
        AuthUser authUser1 = new AuthUser(null, "TestLogin1", "TestPass1", Role.USER, null);
        AuthUser authUser2 = new AuthUser(null, "TestLogin2", "TestPass2", Role.USER, null);
        AuthUser authUser3 = new AuthUser(null, "TestLogin3", "TestPass3", Role.USER, null);
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
                .build();
        Order order2 = new Order.OrderBuilder(car2.getId(), user2.getId())
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-05-01"), ConverterDate.stringToDate("2020-05-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .build();

        session.saveOrUpdate(order1);
        session.saveOrUpdate(order2);

        session.getTransaction().commit();
        session.close();

    }*/

    @Test
    public void saveOrderTest() {
        User user4 = new User(null, "TestName4", "TestLastName4", "test1@gmail.com", false);
        long saveId = userDao.save(user4);
        Car car3 = new Car.CarBuilder(null)
                .withBrand("Lad")
                .withModel("Grant")
                .withYear("2019")
                .withType("Sedan")
                .withPrice(55)
                .build();
        carDao.saveCar(car3);
        Car carById = carDao.getCarById(carDao.getCarIdByBrandAndModelForTest("Lad", "Grant"));

        Order order = new Order.OrderBuilder(carById.getId(), saveId)
                .withPassport("MP3334455")
                .withDates(ConverterDate.stringToDate("2020-03-01"), ConverterDate.stringToDate("2020-03-10"))
                .withTelephone("+375298793307")
                .withPrice(590D)
                .build();
        Long aLong = orderDao.saveOrder(order);

        OrderEntity orderFromDb = sessionFactory.getCurrentSession().get(OrderEntity.class, aLong);

        assertNotNull(order);
        assertEquals(order.getPassport(), orderFromDb.getPassport());
        assertEquals(order.getStartDate(), orderFromDb.getStartDate());

    }


    @Test
    public void getOrdersTest() {
        List<Order> orders = orderDao.getOrders(0, 10);
        assertNotNull(orders);
        assertEquals(orders.get(0).getPassport(), "MP3334456");
        assertEquals(orders.get(0).getPhone(), "+375298793305");
    }

    @Test
    public void getOrdersByStatusTest() {
        Integer countOrdersFromDb = orderDao.getOrdersByStatus(OrderStatus.IN_PROGRESS);
        Integer exp = 2;
        assertNotNull(countOrdersFromDb);
        assertEquals(exp, countOrdersFromDb);
    }

    @Test
    public void setOrderStatusTest() {
        User user4 = new User(null, "TestName6", "TestLastName6", "test1@gmail.com", false);
        long saveId = userDao.save(user4);
        Car car3 = new Car.CarBuilder(null)
                .withBrand("Ladia")
                .withModel("Grantia")
                .withYear("2019")
                .withType("Sedan")
                .withPrice(55)
                .build();
        carDao.saveCar(car3);
        Car carById = carDao.getCarById(carDao.getCarIdByBrandAndModelForTest("Ladia", "Grantia"));

        Order order = new Order.OrderBuilder(carById.getId(), saveId)
                .withPassport("MP3334456")
                .withDates(ConverterDate.stringToDate("2020-03-03"), ConverterDate.stringToDate("2020-03-12"))
                .withTelephone("+375298793305")
                .withPrice(590D)
                .build();
        Long aLong = orderDao.saveOrder(order);

        orderDao.setOrderStatus(aLong, OrderStatus.ACCEPTED);
        Session session = sessionFactory.getCurrentSession();
        OrderEntity orderFromDb = session.get(OrderEntity.class, aLong);

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
    public void getCountRecordsFromOrders() {
        User user4 = new User(null, "TestName9", "TestLastName9", "test1@gmail.com", false);
        long saveId = userDao.save(user4);
        Car car3 = new Car.CarBuilder(null)
                .withBrand("Ladas")
                .withModel("Grantas")
                .withYear("2019")
                .withType("Sedan")
                .withPrice(55)
                .build();
        carDao.saveCar(car3);
        Car carById = carDao.getCarById(carDao.getCarIdByBrandAndModelForTest("Ladas", "Grantas"));

        Order order = new Order.OrderBuilder(carById.getId(), saveId)
                .withPassport("MP3334444")
                .withDates(ConverterDate.stringToDate("2020-03-02"), ConverterDate.stringToDate("2020-03-11"))
                .withTelephone("+375298793306")
                .withPrice(590D)
                .build();
        orderDao.saveOrder(order);
        int countRecordsFromDb = orderDao.getCountRecordsFromOrders();
        int exp = 2;
        assertEquals(exp, countRecordsFromDb);
    }

    @Test
    public void getCountRecordsFromOrdersForUserTest() {
        long id = 2L;
        int countRecordsFromDb = orderDao.getCountRecordsFromOrdersForUser(id);
        int exp = 0;
        assertEquals(exp, countRecordsFromDb);
    }

}
