package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.converter.CarConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.StaffConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.CarEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.StaffEntity;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.model.*;
import com.github.andygo298.rentCarPlatform.model.actions.EditCar;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            TypedQuery<CarEntity> query = session.createQuery("from CarEntity c order by c.id desc", CarEntity.class)
                    .setFirstResult(skipRecords)
                    .setMaxResults(limitRecords);
            List<CarEntity> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList
                    .stream()
                    .map(CarConverter::fromEntity)
                    .collect(Collectors.toList());
        }
    }


    @Override
    public int getCountRecordsFromCar() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<CarEntity> query = session.createQuery("from CarEntity", CarEntity.class);
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
            CarEntity getCar = session.get(CarEntity.class, id);
            session.getTransaction().commit();
            session.close();
            return CarConverter.fromEntity(getCar);
        }
    }

    @Override
    public long getCarIdByBrandAndModelForTest(String brand, String model) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            CarEntity getCar = (CarEntity) session.createQuery("from CarEntity c where c.brand=:brand and c.model=:model")
                    .setParameter("brand", brand)
                    .setParameter("model", model)
                    .getSingleResult();
            session.getTransaction().commit();
            session.close();
            return getCar.getId();
        }
    }

    @Override
    public void editCar(EditCar editCar) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            CarEntity carEntity = session.get(CarEntity.class, editCar.getId());
            carEntity.setBrand(editCar.getBrand());
            carEntity.setModel(editCar.getModel());
            carEntity.setType(editCar.getType());
            carEntity.setYear_mfg(editCar.getYear_mfg());
            carEntity.setImg_url(editCar.getImg_url());
            carEntity.setDay_price(editCar.getDay_price());
            session.saveOrUpdate(carEntity);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void saveCar(Car newCar) {
        CarEntity carEntity = CarConverter.toEntity(newCar);
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(carEntity);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delCar(Long delCarId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();

            CarEntity delCar = session.get(CarEntity.class, delCarId);
            Set<StaffEntity> staff = delCar.getStaff();

            for (StaffEntity staffPerson : staff) {
                CarEntity carRemove = staffPerson.getCarEntitySet()
                        .stream()
                        .filter(carRem -> carRem.equals(delCar))
                        .findFirst()
                        .orElse(null);
                staffPerson.getCarEntitySet().remove(carRemove);
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
            CarEntity carEntity = session.get(CarEntity.class, id);
            carEntity.setIs_rent(status);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void saveStaffIntoCar(Car car, List<Staff> staff) {

      /*  Set<Staff> setStaff = new HashSet<>(staff);
        setStaff.forEach(person -> person.getCar().add(car));
        car.getStaffSet().addAll(setStaff);
        CarEntity carToEntity = CarConverter.toEntity(car);*/

        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            CarEntity carEntity = session.get(CarEntity.class, car.getId());


            Set<StaffEntity> staffEntityList = staff.stream()
//                    .peek(s -> s.setCar(new HashSet<>()))
                    .map(StaffConverter::toEntity).collect(Collectors.toSet());
            staffEntityList.forEach(person -> person.getCarEntitySet().add(carEntity));
            carEntity.getStaff().addAll(staffEntityList);


            session.getTransaction().commit();
            session.close();
        }
    }
}
