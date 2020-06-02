package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.OrderDao;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.dao.converter.CarConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.OrderConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.UserConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.CarEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.OrderEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultOrderDao implements OrderDao {
    private static class SingletonHolder {
        static final OrderDao HOLDER_INSTANCE = new DefaultOrderDao();
    }

    public static OrderDao getInstance() {
        return DefaultOrderDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long saveOrder(Order order) {
        OrderEntity orderEntity = OrderConverter.toEntity(order);
        CarEntity carEntity = CarConverter.toEntity(DefaultCarDao.getInstance().getCarById(orderEntity.getCarId()));
        UserEntity userEntity = UserConverter.toEntity(DefaultUserDao.getInstance().getUserById(orderEntity.getUserId()));
        orderEntity.setCarEntity(carEntity);
        orderEntity.setUserEntity(userEntity);
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(orderEntity);
            Long id = orderEntity.getId();
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    @Override
    public Integer getOrdersByStatus(OrderStatus status) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Object ordSt = session.createQuery
                    ("select count (o.orderStatus) from OrderEntity o where o.orderStatus=:ordSt")
                    .setParameter("ordSt", status)
                    .getSingleResult();
            session.getTransaction().commit();
            session.close();
            return Math.toIntExact((Long) ordSt);
        }
    }

    @Override
    public Integer getUserOrdersByStatus(OrderStatus status, Long userId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Object ordSt = session.createQuery
                    ("select count (o.orderStatus) from OrderEntity o where o.orderStatus=:ordSt and o.userId=:userId")
                    .setParameter("ordSt", status)
                    .setParameter("userId", userId)
                    .getSingleResult();
            session.getTransaction().commit();
            session.close();
            return Math.toIntExact((Long) ordSt);
        }
    }

    //criteria api and pagination
    @Override
    public List<Order> getOrdersByUserId(Long userId, int skipRecords, int limitRecords) {
        try (Session session = SFUtil.getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
            Root<OrderEntity> orderRoot = criteria.from(OrderEntity.class);
            criteria.select(orderRoot)
                    .where(cb.equal(orderRoot.get("userId"), userId))
                    .orderBy(cb.desc(orderRoot.get("id")));
            return session.createQuery(criteria)
                    .setFirstResult(skipRecords)
                    .setMaxResults(limitRecords)
                    .getResultList()
                    .stream()
                    .map(OrderConverter::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Order> getOrders(int skipRecords, int limitRecords) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<OrderEntity> query = session.createQuery("from OrderEntity o order by o.orderStatus desc ", OrderEntity.class)
                    .setFirstResult(skipRecords)
                    .setMaxResults(limitRecords);
            List<OrderEntity> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList
                    .stream()
                    .map(OrderConverter::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public int getCountRecordsFromOrders() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<OrderEntity> query = session.createQuery("from OrderEntity ", OrderEntity.class);
            int resultCount = query.getResultList().size();
            session.getTransaction().commit();
            session.close();
            return resultCount;
        }

    }

    @Override
    public int getCountRecordsFromOrdersForUser(Long userId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<OrderEntity> query = session.createQuery("from OrderEntity o where o.userId in :userId", OrderEntity.class)
                    .setParameter("userId", userId);
            int resultCount = query.getResultList().size();
            session.getTransaction().commit();
            session.close();
            return resultCount;
        }

    }

    @Override
    public Double getOrderPriceById(Long orderId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            OrderEntity getPrice =
                    (OrderEntity) (session.createQuery("from OrderEntity o where o.id in :orderId")
                            .setParameter("orderId", orderId)
                            .getSingleResult());
            session.getTransaction().commit();
            session.close();
            return getPrice.getOrderPrice();
        }
    }

    @Override
    public void setOrderStatus(Long orderId, OrderStatus orderStatus) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            OrderEntity orderEntity = session.get(OrderEntity.class, orderId);
            orderEntity.setOrderStatus(orderStatus);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public Long getCarIdByOrder(Long orderId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            OrderEntity orderEntity = (OrderEntity) session.createQuery("from OrderEntity o where o.id in :orderId")
                    .setParameter("orderId", orderId)
                    .getSingleResult();
            session.getTransaction().commit();
            session.close();
            return orderEntity.getCarId();
        }
    }
}