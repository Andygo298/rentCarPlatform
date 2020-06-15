package com.github.andygo298.rentCarPlatform.service;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.actions.EditCar;
import com.github.andygo298.rentCarPlatform.model.Staff;

import java.util.List;

public interface CarService {
    List<Car> getCars(int page);
    int getCountRecordsFromCar();
    void saveCar(Car newCar);
    void editCar(EditCar editCar);
    void delCar(Long delCarId);
    void changeRentStatus(long id, boolean status);
    Car getCarById(long id);
    void saveStaffIntoCar(Car car, List<Staff> staff);
}
