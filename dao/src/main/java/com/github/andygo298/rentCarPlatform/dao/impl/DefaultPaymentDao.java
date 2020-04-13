package com.github.andygo298.rentCarPlatform.dao.impl;


import com.github.andygo298.rentCarPlatform.dao.ConverterDate;
import com.github.andygo298.rentCarPlatform.dao.DataSource;
import com.github.andygo298.rentCarPlatform.dao.PaymentDao;
import com.github.andygo298.rentCarPlatform.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultPaymentDao implements PaymentDao {

    private static class SingletonHolder {
        static final PaymentDao HOLDER_INSTANCE = new DefaultPaymentDao();
    }

    public static PaymentDao getInstance() {
        return DefaultPaymentDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Payment> getPayments() {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from payment");
             ResultSet rs = ps.executeQuery()) {
            final List<Payment> paymentsList = new ArrayList<>();
            while (rs.next()) {
                final Payment payment = new Payment.PaymentBuilder(rs.getLong("user_id"))
                        .withId(rs.getLong("id"))
                        .withCardNum(rs.getString("card_num"))
                        .withPaymentValue(rs.getDouble("payment_value"))
                        .build();
                paymentsList.add(payment);
            }
            return paymentsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Long savePayment(Payment newPayment) {
        final String sql = "insert into payment(user_id, card_num , payment_date, payment_value) values(?,?,?,?)";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, newPayment.getUserId());
            ps.setString(2,newPayment.getCardNum());
            ps.setDate(3, ConverterDate.stringToDate(newPayment.getPaymentDate()));
            ps.setDouble(4,newPayment.getPaymentValue());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
