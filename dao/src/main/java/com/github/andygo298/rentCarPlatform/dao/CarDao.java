package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;
import com.github.andygo298.rentCarPlatform.model.Staff;

import java.util.List;

public interface CarDao {
    List<Car> getCars(int skipRecords, int limitRecords);
    int getCountRecordsFromCar();
    void saveCar(Car newCar);
    void editCar(EditCar editCar);
    void delCar(Long delCarId);
    void changeRentStatus(long id, boolean status);
    Car getCarById(long id);
    long getCarIdByBrandAndModelForTest(String brand,String model);
    void saveStaffIntoCar(Car car, List<Staff> staff);
}
