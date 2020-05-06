package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.Constant;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultCarDao;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;
import com.github.andygo298.rentCarPlatform.service.CarService;

import java.util.List;

public class DefaultCarService implements CarService {

    private final CarDao carDao = DefaultCarDao.getInstance();

    private static class SingletonHolder {
        static final CarService HOLDER_INSTANCE = new DefaultCarService();
    }

    public static CarService getInstance() {
        return DefaultCarService.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<Car> getCars(int page) {
        int skipRecords = 0;
        if (page != 1) {
            skipRecords = page - 1;
            skipRecords = skipRecords * Constant.LIMIT_RECORDS;
        }
        return carDao.getCars(skipRecords, Constant.LIMIT_RECORDS);
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
    public int getCountPages(int countRecordsFromCar) {
        return (int) Math.ceil(((double)countRecordsFromCar / Constant.LIMIT_RECORDS));
    }
}
