package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.DataSource;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultCarDao implements CarDao {

    private static class SingletonHolder {
        static final CarDao HOLDER_INSTANCE = new DefaultCarDao();
    }

    public static CarDao getInstance() {
        return DefaultCarDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Car> getCars() {
        // TODO: filter not broken cars
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from cars");
             ResultSet rs = ps.executeQuery()) {
            final List<Car> carsList = new ArrayList<>();
            while (rs.next()) {
                final Car car = new Car.CarBuilder(rs.getLong("id"))
                        .withBrand(rs.getString("brand"))
                        .withModel(rs.getString("model"))
                        .withPrice(rs.getDouble("day_price"))
                        .withType(rs.getString("type"))
                        .withYear(rs.getString("year_mfg"))
                        .withImg(rs.getString("img_url"))
                        .withIsRent(rs.getBoolean("isRent"))
                        .build();
                carsList.add(car);
            }
            return carsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car getCarById(long id) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from cars where id = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Car car = null;
                while (rs.next()) {
                    car = new Car.CarBuilder(rs.getLong("id"))
                            .withBrand(rs.getString("brand"))
                            .withModel(rs.getString("model"))
                            .withPrice(rs.getDouble("day_price"))
                            .withType(rs.getString("type"))
                            .withYear(rs.getString("year_mfg"))
                            .withImg(rs.getString("img_url"))
                            .build();
                }
                return car;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editCar(EditCar editCar) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("update cars set brand = ?, model = ?, type = ?, " +
                     "year_mfg = ?, img_url = ?, day_price = ? where id = ?")) {
            ps.setString(1, editCar.getBrand());
            ps.setString(2, editCar.getModel());
            ps.setString(3, editCar.getType());
            ps.setString(4, editCar.getYear_mfg());
            ps.setString(5, editCar.getImg_url());
            ps.setDouble(6, editCar.getDay_price());
            ps.setLong(7, editCar.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveCar(Car newCar) {
        final String sql = "insert into cars(brand, model, type, year_mfg, day_price) values(?,?,?,?,?)";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, newCar.getBrand());
            ps.setString(2, newCar.getModel());
            ps.setString(3, newCar.getType());
            ps.setString(4, newCar.getYear_mfg());
            ps.setDouble(5, newCar.getDay_price());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                keys.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delCar(Long delCarId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("delete from cars where id = ?")) {
            ps.setLong(1, delCarId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeRentStatus(long id, boolean status) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("update cars set isRent=? where id = ?")) {
            ps.setBoolean(1, status);
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
