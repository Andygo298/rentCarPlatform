package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.utils.Constant;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultCarDao;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.actions.EditCar;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;

import java.util.List;

public class DefaultCarService implements CarService {

    private static class SingletonHolder {
        static final CarService HOLDER_INSTANCE = new DefaultCarService();
    }

    public static CarService getInstance() {
        return DefaultCarService.SingletonHolder.HOLDER_INSTANCE;
    }

    private CarDao carDao = DefaultCarDao.getInstance();


    @Override
    public List<Car> getCars(int page) {
        return carDao.getCars(ServiceUtil.getSkipRecords(page), Constant.LIMIT_RECORDS);
    }

    @Override
    public int getCountRecordsFromCar() {
        return carDao.getCountRecordsFromCar();
    }

    @Override
    public void saveCar(Car newCar) {
        carDao.saveCar(newCar);
    }

    @Override
    public void editCar(EditCar editCar) {
        carDao.editCar(editCar);
    }

    @Override
    public void delCar(Long delCarId) {
        carDao.delCar(delCarId);
    }

    @Override
    public void changeRentStatus(long id, boolean status) {
        carDao.changeRentStatus(id, status);
    }

    @Override
    public Car getCarById(long id) {
        return carDao.getCarById(id);
    }

    @Override
    public void saveStaffIntoCar(Car car, List<Staff> staff) {
        carDao.saveStaffIntoCar(car,staff);
    }
}
