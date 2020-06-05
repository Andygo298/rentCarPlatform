package com.github.andygo298.rentCarPlatform.dao.converter;

import com.github.andygo298.rentCarPlatform.dao.entity.CarEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.StaffEntity;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultStaffDao;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.Staff;

import java.util.Set;
import java.util.stream.Collectors;

public class CarConverter {

    public static Car fromEntity(CarEntity carEntity) {
        if (carEntity == null) {
            return null;
        }
        return new Car(
                    carEntity.getId(),
                    carEntity.getBrand(),
                    carEntity.getModel(),
                    carEntity.getType(),
                    carEntity.getYear_mfg(),
                    carEntity.getImg_url(),
                    carEntity.getDay_price(),
                    carEntity.isIs_rent(),
                    carEntity.getStaff()
                            .stream()
                            .map(StaffConverter::fromEntity)
                            .collect(Collectors.toSet())
            );
    }

    public static CarEntity toEntity(Car car) {
        if (car == null) {
            return null;
        }
        final CarEntity carEntity = new CarEntity();
        carEntity.setId(car.getId());
        carEntity.setBrand(car.getBrand());
        carEntity.setModel(car.getModel());
        carEntity.setType(car.getType());
        carEntity.setYear_mfg(car.getYear_mfg());
        carEntity.setImg_url(car.getImg_url());
        carEntity.setDay_price(car.getDay_price());
        carEntity.setIs_rent(car.isIs_rent());
        return carEntity;
    }

    public static Car fromEntityPostConverter(CarEntity carEntity) {
        if (carEntity == null) {
            return null;
        }
        return new Car(
                carEntity.getId(),
                carEntity.getBrand(),
                carEntity.getModel(),
                carEntity.getType(),
                carEntity.getYear_mfg(),
                carEntity.getImg_url(),
                carEntity.getDay_price(),
                carEntity.isIs_rent(),
                null
        );
    }

    public static CarEntity toEntityPostConverter(Car car) {
        if (car == null) {
            return null;
        }
        final CarEntity carEntity = new CarEntity();
        carEntity.setId(car.getId());
        carEntity.setBrand(car.getBrand());
        carEntity.setModel(car.getModel());
        carEntity.setType(car.getType());
        carEntity.setYear_mfg(car.getYear_mfg());
        carEntity.setImg_url(car.getImg_url());
        carEntity.setDay_price(car.getDay_price());
        carEntity.setIs_rent(car.isIs_rent());
        carEntity.setStaff(null);
        return carEntity;
    }
}
