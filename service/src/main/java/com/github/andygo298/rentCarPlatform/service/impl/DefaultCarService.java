package com.github.andygo298.rentCarPlatform.service.impl;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultCarDao;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;
import com.github.andygo298.rentCarPlatform.service.CarService;

import java.util.List;

public class DefaultCarService implements CarService {

    private static class SingletonHolder {
        static final CarService HOLDER_INSTANCE = new DefaultCarService();
    }

    public static CarService getInstance() {
        return DefaultCarService.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<Car> getCars() {
       return DefaultCarDao.getInstance().getCars();
    }

    @Override
    public void saveCar(Car newCar) {
        DefaultCarDao.getInstance().saveCar(newCar);
    }

    @Override
    public void editCar(EditCar editCar) {
        DefaultCarDao.getInstance().editCar(editCar);
    }

    @Override
    public void delCar(Long delCarId) {
        DefaultCarDao.getInstance().delCar(delCarId);
    }

    @Override
    public void changeRentStatus(long id, boolean status) {
         DefaultCarDao.getInstance().changeRentStatus(id, status);
    }

    @Override
    public Car getCarById(long id) {
        return DefaultCarDao.getInstance().getCarById(id);
    }
}
