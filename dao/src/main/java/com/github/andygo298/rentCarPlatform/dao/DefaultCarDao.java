package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.*;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
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
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<Car> query = session.createQuery("from Car", Car.class);
            List<Car> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList;
        }
    }

    @Override
    public Car getCarById(long id) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Car getCar = session.get(Car.class, id);
            session.getTransaction().commit();
            return getCar;
        }
    }

    @Override
    public long getCarIdByBrandAndModelForTest(String brand, String model) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Object getCar = session.createQuery("from Car c where c.brand=:brand and c.model=:model")
                    .setParameter("brand", brand)
                    .setParameter("model", model)
                    .getSingleResult();
            session.getTransaction().commit();
            session.close();
            return ((Car) getCar).getId();
        }
    }

    @Override
    public void editCar(EditCar editCar) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Car car = session.get(Car.class, editCar.getId());
            car.setBrand(editCar.getBrand());
            car.setModel(editCar.getModel());
            car.setType(editCar.getType());
            car.setYear_mfg(editCar.getYear_mfg());
            car.setImg_url(editCar.getImg_url());
            car.setDay_price(editCar.getDay_price());
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void saveCar(Car newCar) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(newCar);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delCar(Long delCarId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Car delCar = session.get(Car.class, delCarId);
            session.delete(delCar);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void changeRentStatus(long id, boolean status) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Car car = session.get(Car.class, id);
            car.setIs_rent(status);
            session.getTransaction().commit();
            session.close();
        }
    }

}
