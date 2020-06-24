package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.converter.CarConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.StaffConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.CarEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.StaffEntity;
import com.github.andygo298.rentCarPlatform.model.*;
import com.github.andygo298.rentCarPlatform.model.actions.EditCar;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultCarDao implements CarDao {
    private final SessionFactory sessionFactory;

    public DefaultCarDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Car> getCars(int skipRecords, int limitRecords) {
        TypedQuery<CarEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from CarEntity c order by c.id desc", CarEntity.class)
                .setFirstResult(skipRecords)
                .setMaxResults(limitRecords);
        List<CarEntity> resultList = query.getResultList();
        return resultList
                .stream()
                .map(CarConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public int getCountRecordsFromCar() {
        TypedQuery<CarEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from CarEntity", CarEntity.class);
        return query.getResultList().size();

    }

    @Override
    public Car getCarById(long id) {
        CarEntity getCar = sessionFactory.getCurrentSession().get(CarEntity.class, id);
        return CarConverter.fromEntity(getCar);
    }

    @Override
    public long getCarIdByBrandAndModelForTest(String brand, String model) {
        CarEntity getCar = (CarEntity) sessionFactory.getCurrentSession()
                .createQuery("from CarEntity c where c.brand=:brand and c.model=:model")
                .setParameter("brand", brand)
                .setParameter("model", model)
                .getSingleResult();
        return getCar.getId();
    }

    @Override
    public void editCar(EditCar editCar) {
        CarEntity carEntity = sessionFactory.getCurrentSession().get(CarEntity.class, editCar.getId());
        carEntity.setBrand(editCar.getBrand());
        carEntity.setModel(editCar.getModel());
        carEntity.setType(editCar.getType());
        carEntity.setYear_mfg(editCar.getYear_mfg());
        carEntity.setImg_url(editCar.getImg_url());
        carEntity.setDay_price(editCar.getDay_price());
        sessionFactory.getCurrentSession().update(carEntity);
    }

    @Override
    public void saveCar(Car newCar) {
        CarEntity carEntity = CarConverter.toEntity(newCar);
        sessionFactory.getCurrentSession().saveOrUpdate(carEntity);
    }

    @Override
    public void delCar(Long delCarId) {
        CarEntity delCar = sessionFactory.getCurrentSession().get(CarEntity.class, delCarId);
        List<StaffEntity> staff = delCar.getStaff();

        for (StaffEntity staffPerson : staff) {
            CarEntity carRemove = staffPerson.getCarEntitySet()
                    .stream()
                    .filter(carRem -> carRem.equals(delCar))
                    .findFirst()
                    .orElse(null);
            staffPerson.getCarEntitySet().remove(carRemove);
        }

        sessionFactory.getCurrentSession().delete(delCar);
    }

    @Override
    public void changeRentStatus(long id, boolean status) {
        CarEntity carEntity = sessionFactory.getCurrentSession().get(CarEntity.class, id);
        carEntity.setIs_rent(status);
    }

    @Override
    public void saveStaffIntoCar(Car car, List<Staff> staff) {
        CarEntity carEntity = sessionFactory.getCurrentSession().get(CarEntity.class, car.getId());

        List<StaffEntity> staffEntityList = staff
                .stream()
                .map(StaffConverter::toEntity)
                .collect(Collectors.toList());
        staffEntityList.forEach(person -> person.getCarEntitySet().add(carEntity));
        carEntity.getStaff().addAll(staffEntityList);

        sessionFactory.getCurrentSession().update(carEntity);
    }
}
