package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.OrderDao;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.converter.CarConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.OrderConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.UserConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.CarEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.OrderEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultOrderDao implements OrderDao {
    private final SessionFactory sessionFactory;
    private final UserDao userDao;
    private final CarDao carDao;

    public DefaultOrderDao(SessionFactory sessionFactory, UserDao userDao, CarDao carDao) {
        this.sessionFactory = sessionFactory;
        this.userDao = userDao;
        this.carDao = carDao;
    }

    @Override
    public Long saveOrder(Order order) {
        OrderEntity orderEntity = OrderConverter.toEntity(order);
        CarEntity carEntity = CarConverter.toEntity(carDao.getCarById(orderEntity.getCarId()));
        UserEntity userEntity = UserConverter.toEntity(userDao.getUserById(orderEntity.getUserId()));
        orderEntity.setCarEntity(carEntity);
        orderEntity.setUserEntity(userEntity);
        sessionFactory.getCurrentSession().saveOrUpdate(orderEntity);
        return orderEntity.getId();
    }

    @Override
    public Integer getOrdersByStatus(OrderStatus status) {
        Object ordSt = sessionFactory.getCurrentSession()
                .createQuery("select count (o.orderStatus) from OrderEntity o where o.orderStatus=:ordSt")
                .setParameter("ordSt", status)
                .getSingleResult();
        return Math.toIntExact((Long) ordSt);
    }

    @Override
    public Integer getUserOrdersByStatus(OrderStatus status, Long userId) {
        Object ordSt = sessionFactory.getCurrentSession()
                .createQuery("select count (o.orderStatus) from OrderEntity o where o.orderStatus=:ordSt and o.userId=:userId")
                .setParameter("ordSt", status)
                .setParameter("userId", userId)
                .getSingleResult();
        return Math.toIntExact((Long) ordSt);
    }

    //criteria api and pagination
    @Override
    public List<Order> getOrdersByUserId(Long userId, int skipRecords, int limitRecords) {
        CriteriaBuilder cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> orderRoot = criteria.from(OrderEntity.class);
        criteria.select(orderRoot)
                .where(cb.equal(orderRoot.get("userId"), userId))
                .orderBy(cb.desc(orderRoot.get("id")));
        return sessionFactory.getCurrentSession()
                .createQuery(criteria)
                .setFirstResult(skipRecords)
                .setMaxResults(limitRecords)
                .getResultList()
                .stream()
                .map(OrderConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrders(int skipRecords, int limitRecords) {
        TypedQuery<OrderEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from OrderEntity o order by o.orderStatus desc ", OrderEntity.class)
                .setFirstResult(skipRecords)
                .setMaxResults(limitRecords);
        List<OrderEntity> resultList = query.getResultList();
        return resultList
                .stream()
                .map(OrderConverter::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public int getCountRecordsFromOrders() {
        TypedQuery<OrderEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from OrderEntity ", OrderEntity.class);
        return query.getResultList().size();
    }

    @Override
    public int getCountRecordsFromOrdersForUser(Long userId) {
        TypedQuery<OrderEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from OrderEntity o where o.userId in :userId", OrderEntity.class)
                .setParameter("userId", userId);
        return query.getResultList().size();
    }

    @Override
    public Double getOrderPriceById(Long orderId) {
        OrderEntity getPrice = (OrderEntity) (sessionFactory.getCurrentSession()
                .createQuery("from OrderEntity o where o.id in :orderId")
                .setParameter("orderId", orderId)
                .getSingleResult());
        return getPrice.getOrderPrice();

    }

    @Override
    public void setOrderStatus(Long orderId, OrderStatus orderStatus) {
        OrderEntity orderEntity = sessionFactory.getCurrentSession().get(OrderEntity.class, orderId);
        orderEntity.setOrderStatus(orderStatus);
    }

    @Override
    public Long getCarIdByOrder(Long orderId) {
        OrderEntity orderEntity = (OrderEntity) sessionFactory.getCurrentSession()
                .createQuery("from OrderEntity o where o.id in :orderId")
                .setParameter("orderId", orderId)
                .getSingleResult();
        return orderEntity.getCarId();
    }
}