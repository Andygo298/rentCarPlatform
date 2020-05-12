package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class DefaultCarServiceTest {

    @Mock
    CarDao carDao;

    @InjectMocks
    DefaultCarService defaultCarService;


    @Test
    void getInstanceTest() {
        CarService instance = DefaultCarService.getInstance();
        assertNotNull(instance);
    }

    @Test
    void getCarsTest() {
        Car car1 = new Car.CarBuilder(1L)
                .withBrand("Renault")
                .withModel("Arkana")
                .withType("SUV")
                .withYear("2019")
                .build();
        Car car2 = new Car.CarBuilder(2L)
                .withBrand("Renault")
                .withModel("Duster")
                .withType("SUV")
                .withYear("2015")
                .build();
        int page = 1;
        List<Car> cars = Arrays.asList(car1, car2);
        when(carDao.getCars(0, 3)).thenReturn(cars);
        List<Car> carsFromDb = defaultCarService.getCars(1);
        assertEquals(cars, carsFromDb);
    }
    @Test
    void getCarsPageTwoNotExist(){
        int countPages = ServiceUtil.getCountPages(3);
        when(carDao.getCars(0, 3)).thenReturn(null);
        List<Car> carsFromDb = defaultCarService.getCars(countPages);
        assertNull(carsFromDb);
    }

    @Test
    void getCountRecordsFromCarTest() {
        Car car1 = new Car.CarBuilder(1L)
                .withBrand("Renault")
                .withModel("Arkana")
                .withType("SUV")
                .withYear("2019")
                .build();
        Car car2 = new Car.CarBuilder(2L)
                .withBrand("Renault")
                .withModel("Duster")
                .withType("SUV")
                .withYear("2015")
                .build();
        carDao.saveCar(car1);
        carDao.saveCar(car2);
        when(carDao.getCountRecordsFromCar()).thenReturn(2);
        int countRecordsFromDb = defaultCarService.getCountRecordsFromCar();
        assertEquals(2, countRecordsFromDb);
    }

    @Test
    void saveCarTest() {
        Car car = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Arkana")
                .withType("SUV")
                .withYear("2019")
                .build();
        doNothing().when(carDao).saveCar(car);
        defaultCarService.saveCar(car);
        verify(carDao, times(1)).saveCar(car);
    }

    @Test
    void editCarTest() {
        EditCar carToEdit = new EditCar.CarBuilder(2L)
                .withBrand("Renault")
                .withModel("Duster")
                .withType("SUV")
                .withYear("2014")
                .build();
        doNothing().when(carDao).editCar(carToEdit);
        defaultCarService.editCar(carToEdit);
        verify(carDao, times(1)).editCar(carToEdit);
    }

    @Test
    void delCarTest() {
        doNothing().when(carDao).delCar(anyLong());
        defaultCarService.delCar(1L);
        verify(carDao, times(1)).delCar(1L);
    }

    @Test
    void delCarWrongTest() {
        doThrow(NullPointerException.class).when(carDao).delCar(anyLong());
        assertThrows(NullPointerException.class, () -> defaultCarService.delCar(1L));
        verify(carDao, times(1)).delCar(1L);
    }

    @Test
    void changeRentStatusTest() {
        doNothing().when(carDao).changeRentStatus(anyLong(),anyBoolean());
        defaultCarService.changeRentStatus(5L,true);
        verify(carDao, times(1)).changeRentStatus(5L,true);
    }

    @Test
    void getCarByIdTest() {
        Car mockCar = new Car.CarBuilder(5L)
                .withBrand("Renault")
                .withModel("Arkana")
                .withType("SUV")
                .withYear("2019")
                .build();
        when(carDao.getCarById(5L)).thenReturn(mockCar);
        Car carActual = defaultCarService.getCarById(5L);
        assertNotNull(carActual);
        assertEquals(mockCar, carActual);
    }

    @Test
    void getCarByIdNotExistTest(){
        when(carDao.getCarById(10L)).thenReturn(null);
        Car carByIdFromDb = defaultCarService.getCarById(10L);
        assertNull(carByIdFromDb);
    }

}