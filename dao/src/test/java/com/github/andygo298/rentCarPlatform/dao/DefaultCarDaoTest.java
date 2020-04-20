package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.dao.impl.DefaultCarDao;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCarDaoTest {

    final CarDao carDao = DefaultCarDao.getInstance();

    @Test
    void saveCarTest(){
        final Car newCar = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Arkana")
                .withType("SUV")
                .withYear("2019")
                .withImg("google.com")
                .withPrice(78)
                .build();
        carDao.saveCar(newCar);
        long idCarActual = carDao.getCarIdByBrandAndModelForTest(newCar.getBrand(),newCar.getModel());
        Car expCar = carDao.getCarById(idCarActual);
        assertEquals(expCar.getBrand(),"Renault");
        assertEquals(expCar.getModel(),"Arkana");
        assertEquals(expCar.getType(),"SUV");
        assertFalse(expCar.isIs_rent());
    }
    @Test
    void testGetCarById(){
       final Long carId = 8L;
       String expModel = "Vesta";
       String expYear = "2019";
       final Car actualCar = carDao.getCarById(carId);
       assertEquals(carId,actualCar.getId());
       assertEquals(expModel,actualCar.getModel());
       assertEquals(expYear,actualCar.getYear_mfg());
    }

    @Test
    void editCarTest(){
        long idCar = 8L;
        final EditCar editCar = new EditCar.CarBuilder(idCar)
                .withBrand("Lada")
                .withModel("Vesta")
                .withType("Sedan")
                .withYear("2019")
                .withImg("google.com")
                .withPrice(65)
                .build();
         carDao.editCar(editCar);
         Car carActual = carDao.getCarById(idCar);
         assertEquals(editCar.getModel(),carActual.getModel());
         assertEquals(editCar.getType(),carActual.getType());
         assertEquals(editCar.getYear_mfg(),carActual.getYear_mfg());
    }
    @Test
    void getCarsTest(){
        List<Car> actualListCars = carDao.getCars();
        String expModel = "Vesta";
        String expYearMfg = "2019";
        String expImgUrl = "google.com";
        assertEquals(expModel,actualListCars.get(0).getModel());
        assertEquals(expYearMfg,actualListCars.get(0).getYear_mfg());
        assertEquals(expImgUrl,actualListCars.get(0).getImg_url());
    }
    @Test
    void changeRentStatus(){
        long id = 8L;
        carDao.changeRentStatus(id, true);
        Car actualCar = carDao.getCarById(id);
        assertTrue(actualCar.isIs_rent());

        carDao.changeRentStatus(id, false);
        Car actualFalseCar = carDao.getCarById(id);
        assertFalse(actualFalseCar.isIs_rent());
    }
    @Test
    void deleteCarTest(){
        long delCarId = carDao.getCarIdByBrandAndModelForTest("Renault", "Arkana");
        carDao.delCar(delCarId);
        Car actualCar = carDao.getCarById(delCarId);
        assertNull(actualCar);
    }
}
