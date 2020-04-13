package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.ConverterDate;
import com.github.andygo298.rentCarPlatform.dao.DataSource;
import com.github.andygo298.rentCarPlatform.dao.OrderDao;
import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.OrderStatus;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultOrderDao implements OrderDao {
    private static class SingletonHolder {
        static final OrderDao HOLDER_INSTANCE = new DefaultOrderDao();
    }

    public static OrderDao getInstance() {
        return DefaultOrderDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long saveOrder(Order order) {
        Date dateStart = ConverterDate.stringToDate(order.getStartDate());
        Date dateEnd = ConverterDate.stringToDate(order.getEndDate());

        final String sql = "insert into orders(passport, phone, start_date, end_date, " +
                "cars_id, user_id, status, order_price) values(?,?,?,?,?,?,?,?)";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getPassport());
            ps.setString(2, order.getPhone());
            ps.setDate(3, dateStart);
            ps.setDate(4, dateEnd);
            ps.setLong(5, order.getCarId());
            ps.setLong(6, order.getUserId());
            ps.setString(7, order.getOrderStatus().name());
            ps.setDouble(8, order.getOrderPrice());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getOrdersByStatus(OrderStatus status) {
        final String sql = "select count(orders.status) from orders where status=?";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status.name());
            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count = rs.getInt(1);
                }
                return count;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getUserOrdersByStatus(OrderStatus status, Long userId) {
        final String sql = "select count(orders.status) from orders where status=? and user_id=?";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setLong(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count = rs.getInt(1);
                }
                return count;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        final String sql = "select * from orders where user_id=?";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            return getOrdersList(ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getOrders() {
        final String sql = "select * from orders";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return getOrdersList(ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getOrderPriceById(Long orderId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from orders where id = ?")) {
            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                Double orderPrice = null;
                while (rs.next()) {
                    orderPrice = rs.getDouble("order_price");
                }
                return orderPrice;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void setOrderStatus(Long orderId, OrderStatus orderStatus) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("update orders set status=? where id = ?")) {
            ps.setString(1, orderStatus.name());
            ps.setLong(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long getCarIdByOrder(Long orderId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("select cars_id from orders where cars_id = ?")) {
            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                Long carId = null;
                while (rs.next()) {
                    carId = rs.getLong(1);
                }
                return carId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Order> getOrdersList(PreparedStatement ps) {
        try (ResultSet rs = ps.executeQuery()) {
            final List<Order> orderList = new ArrayList<>();
            while (rs.next()) {
                final Order order = getOrderInstance(rs);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    private Order getOrderInstance(ResultSet rs) throws SQLException {
        Long carId = rs.getLong("cars_id");
        Long userId = rs.getLong("user_id");
        String start = ConverterDate.dateToString(rs.getDate("start_date"));
        String end = ConverterDate.dateToString(rs.getDate("end_date"));
        return new Order.OrderBuilder(carId, userId)
                .withId(rs.getLong("id"))
                .withPassport(rs.getString("passport"))
                .withDates(start, end)
                .withTelephone(rs.getString("phone"))
                .withPrice(rs.getDouble("order_price"))
                .withStatus(OrderStatus.valueOf(rs.getString("status")))
                .build();
    }


}
