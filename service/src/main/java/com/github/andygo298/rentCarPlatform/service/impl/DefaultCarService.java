package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.utils.Constant;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.actions.EditCar;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultCarService implements CarService {

    private final CarDao carDao;

    public DefaultCarService(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    @Transactional
    public List<Car> getCars(int page) {
        return carDao.getCars(ServiceUtil.getSkipRecords(page), Constant.LIMIT_RECORDS);
    }

    @Override
    @Transactional
    public int getCountRecordsFromCar() {
        return carDao.getCountRecordsFromCar();
    }

    @Override
    @Transactional
    public void saveCar(Car newCar) {
        carDao.saveCar(newCar);
    }

    @Override
    @Transactional
    public void editCar(EditCar editCar) {
        carDao.editCar(editCar);
    }

    @Override
    @Transactional
    public void delCar(Long delCarId) {
        carDao.delCar(delCarId);
    }

    @Override
    @Transactional
    public void changeRentStatus(long id, boolean status) {
        carDao.changeRentStatus(id, status);
    }

    @Override
    @Transactional
    public Car getCarById(long id) {
        return carDao.getCarById(id);
    }

    @Override
    @Transactional
    public void saveStaffIntoCar(Car car, List<Staff> staff) {
        carDao.saveStaffIntoCar(car,staff);
    }
}
