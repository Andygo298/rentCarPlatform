package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.model.*;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DefaultCarDao implements CarDao {

    private static class SingletonHolder {
        static final CarDao HOLDER_INSTANCE = new DefaultCarDao();
    }

    public static CarDao getInstance() {
        return DefaultCarDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Car> getCars(int skipRecords, int limitRecords) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<Car> query = session.createQuery("from Car c order by c.id desc", Car.class)
                    .setFirstResult(skipRecords)
                    .setMaxResults(limitRecords);
            List<Car> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList;
        }
    }

    @Override
    public int getCountRecordsFromCar() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<Car> query = session.createQuery("from Car", Car.class);
            int resultCount = query.getResultList().size();
            session.getTransaction().commit();
            session.close();
            return resultCount;
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
            Set<Staff> staff = delCar.getStaff();

            for (Staff staffPerson : staff) {
                Car carRemove = staffPerson.getCar().stream().filter(carRem -> carRem.equals(delCar)).findFirst().orElse(null);
                staffPerson.getCar().remove(carRemove);
            }

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

    @Override
    public void saveStaffIntoCar(Car car, List<Staff> staff) {
        car.getStaff().addAll(staff);
        for (Staff person : staff) {
            person.getCar().add(car);
        }
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            for (Staff person : staff) {
                session.saveOrUpdate(person);
            }
            session.getTransaction().commit();
            session.close();
        }
    }
}
