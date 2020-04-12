package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;

import java.util.List;

public interface CarDao {
    List<Car> getCars();
    void saveCar(Car newCar);
    void editCar(EditCar editCar);
    void delCar(Long delCarId);
    void changeRentStatus(long id, boolean status);
    Car getCarById(long id);
}
