package com.github.andygo298.rentCarPlatform.service.impl;
import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.DefaultCarDao;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;
import com.github.andygo298.rentCarPlatform.service.CarService;

import java.util.List;

public class DefaultCarService implements CarService {

    private final CarDao instance = DefaultCarDao.getInstance();

    private static class SingletonHolder {
        static final CarService HOLDER_INSTANCE = new DefaultCarService();
    }

    public static CarService getInstance() {
        return DefaultCarService.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<Car> getCars() {
       return instance.getCars();
    }

    @Override
    public void saveCar(Car newCar) {
        instance.saveCar(newCar);
    }

    @Override
    public void editCar(EditCar editCar) {
        instance.editCar(editCar);
    }

    @Override
    public void delCar(Long delCarId) {
        instance.delCar(delCarId);
    }

    @Override
    public void changeRentStatus(long id, boolean status) {
         instance.changeRentStatus(id, status);
    }

    @Override
    public Car getCarById(long id) {
        return instance.getCarById(id);
    }
}
