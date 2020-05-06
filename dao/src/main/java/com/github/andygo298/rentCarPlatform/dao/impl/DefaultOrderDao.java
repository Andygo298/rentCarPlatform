package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.OrderDao;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.OrderStatus;
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
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(order);
            Long id = order.getId();
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
                    ("select count (o.orderStatus) from Order o where o.orderStatus=:ordSt")
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
                    ("select count (o.orderStatus) from Order o where o.orderStatus=:ordSt and o.userId=:userId")
                    .setParameter("ordSt", status)
                    .setParameter("userId", userId)
                    .getSingleResult();
            session.getTransaction().commit();
            session.close();
            return Math.toIntExact((Long) ordSt);
        }
    }

    //criteria api
    @Override
    public List<Order> getOrdersByUserId(Long userId, int skipRecords, int limitRecords) {
        try (Session session = SFUtil.getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Order> cr = cb.createQuery(Order.class);
            Root<Order> root = cr.from(Order.class);
            cr.select(root)
                    .where(cb.equal(root.get("userId"), userId))
                    .orderBy(cb.desc(root.get("id")));
            return session.createQuery(cr).getResultList()
                    .stream()
                    .skip(skipRecords)
                    .limit(limitRecords)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Order> getOrders(int skipRecords, int limitRecords) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<Order> query = session.createQuery("from Order o order by o.orderStatus desc ", Order.class);
            List<Order> resultList = query.getResultList()
                    .stream()
                    .skip(skipRecords)
                    .limit(limitRecords)
                    .collect(Collectors.toList());
            session.getTransaction().commit();
            session.close();
            return resultList;
        }
    }

    @Override
    public int getCountRecordsFromOrders() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<Order> query = session.createQuery("from Order ", Order.class);
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
            TypedQuery<Order> query = session.createQuery("from Order o where o.userId in :userId", Order.class)
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
            Order getPriceOrder =
                    (Order) (session.createQuery("from Order o where o.id in :orderId")
                            .setParameter("orderId", orderId)
                            .getSingleResult());
            session.getTransaction().commit();
            session.close();
            return getPriceOrder.getOrderPrice();
        }
    }

    @Override
    public void setOrderStatus(Long orderId, OrderStatus orderStatus) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Order order = session.get(Order.class, orderId);
            order.setOrderStatus(orderStatus);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public Long getCarIdByOrder(Long orderId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Order order = (Order) session.createQuery("from Order o where o.id in :orderId")
                    .setParameter("orderId", orderId)
                    .getSingleResult();
            session.getTransaction().commit();
            session.close();
            return order.getCarId();
        }
    }
}