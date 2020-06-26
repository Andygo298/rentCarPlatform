package Impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.StaffDao;
import com.github.andygo298.rentCarPlatform.dao.config.DaoConfig;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.actions.EditCar;
import com.github.andygo298.rentCarPlatform.model.enums.Specialization;
import com.github.andygo298.rentCarPlatform.model.Staff;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultCarEntityDaoTest {

    @Autowired
    private CarDao carDao;
    @Autowired
    private StaffDao staffDao;

    @Test
    void saveCarTest() {
        final Car newCar = new Car.CarBuilder(null)
                .withBrand("Opel")
                .withModel("Vectra")
                .withType("Sedan")
                .withYear("2001")
                .withImg("google.com")
                .withPrice(78)
                .build();
        carDao.saveCar(newCar);
        long idCarActual = carDao.getCarIdByBrandAndModelForTest(newCar.getBrand(), newCar.getModel());
        Car expCar = carDao.getCarById(idCarActual);
        assertEquals(expCar.getBrand(), "Opel");
        assertEquals(expCar.getModel(), "Vectra");
        assertEquals(expCar.getType(), "Sedan");
        assertFalse(expCar.isIs_rent());
    }
    @Test
    void saveStaffIntoCarTest(){
        Car car = new Car.CarBuilder(null)
                .withBrand("t")
                .withModel("t")
                .withType("SUV")
                .withYear("2010")
                .withImg("google.com")
                .withPrice(50)
                .build();
        Staff staff1 = new Staff.StaffBuilder()
                .withFirstName("t11")
                .withLastName("t11")
                .withSpecialization(Specialization.CLEANER)
                .withCar(new ArrayList<>())
                .build();
        Staff staff2 = new Staff.StaffBuilder()
                .withFirstName("t22")
                .withLastName("t22")
                .withSpecialization(Specialization.DRIVER)
                .withCar(new ArrayList<>())
                .build();
        carDao.saveCar(car);
        Car carfromDb = carDao.getCarById(carDao.getCarIdByBrandAndModelForTest("t", "t"));
        Long aLong1 = staffDao.saveStaff(staff1);
        Long aLong2 = staffDao.saveStaff(staff2);
        Staff personById1 = staffDao.getPersonById(aLong1);
        Staff personById2 = staffDao.getPersonById(aLong2);
        List<Staff> staff = Arrays.asList(personById1, personById2);
        Car carById = carDao.getCarById(carfromDb.getId());

        carDao.saveStaffIntoCar(carById,staff);
        long idCarActual = carDao.getCarIdByBrandAndModelForTest(car.getBrand(), car.getModel());
        Car expCar = carDao.getCarById(idCarActual);
        Staff exp0 = expCar.getStaffSet().get(0);
        assertNotNull(exp0);
        assertEquals(exp0.getFirstName(), staff1.getFirstName());
    }

    @Test
    void testGetCarByIdTest() {
        Car car = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Arkana")
                .withType("SUV")
                .withYear("2019")
                .withImg("google.com")
                .withPrice(78)
                .build();
        carDao.saveCar(car);
        final Long carId = 1L;
        String expModel = "testEdit";
        String expYear = "2019";
        final Car actualCar = carDao.getCarById(carId);
        assertEquals(carId, actualCar.getId());
        assertEquals(expModel, actualCar.getModel());
        assertEquals(expYear, actualCar.getYear_mfg());
    }

    @Test
    void editCarTest() {
        Car car = new Car.CarBuilder(null)
                .withBrand("testEdit")
                .withModel("testEdit")
                .withType("SUV")
                .withYear("2019")
                .withImg("google.com")
                .withPrice(78)
                .build();
        carDao.saveCar(car);
        long idCarActual = carDao.getCarIdByBrandAndModelForTest(car.getBrand(), car.getModel());
        Car expCar = carDao.getCarById(idCarActual);
        final EditCar editCar = new EditCar.CarBuilder(expCar.getId())
                .withBrand("Lada")
                .withModel("Vesta")
                .withType("Sedan")
                .withYear("2019")
                .withImg("google.com")
                .withPrice(65)
                .build();
        carDao.editCar(editCar);
        Car carActual = carDao.getCarById(expCar.getId());
        assertEquals(editCar.getId(), carActual.getId());
        assertEquals(editCar.getBrand(), carActual.getBrand());
        assertEquals(editCar.getModel(), carActual.getModel());
        assertEquals(editCar.getType(), carActual.getType());
        assertEquals(editCar.getYear_mfg(), carActual.getYear_mfg());
        assertEquals(editCar.getImg_url(), carActual.getImg_url());
        assertEquals(editCar.getDay_price(), carActual.getDay_price());
    }

    @Test
    void getCarsTest() {
        List<Car> actualListCars = carDao.getCars(0, 10);
        String expModel = "Vectra";
        String expYearMfg = "2001";
        String expImgUrl = "google.com";
        assertEquals(expModel, actualListCars.get(0).getModel());
        assertEquals(expYearMfg, actualListCars.get(0).getYear_mfg());
        assertEquals(expImgUrl, actualListCars.get(0).getImg_url());
    }

    @Test
    public void getCountRecordsFromCarTest() {
        int countRecordsFromDb = carDao.getCountRecordsFromCar();
        int exp = 2;
        assertEquals(exp, countRecordsFromDb);
    }

    @Test
    void changeRentStatusTest() {
        Car car = new Car.CarBuilder(null)
                .withBrand("testRent")
                .withModel("testRent")
                .withType("SUV")
                .withYear("2019")
                .withImg("google.com")
                .withPrice(78)
                .build();
        carDao.saveCar(car);
        long idCarActual = carDao.getCarIdByBrandAndModelForTest(car.getBrand(), car.getModel());
        Car expCar = carDao.getCarById(idCarActual);

        carDao.changeRentStatus(expCar.getId(), true);
        Car actualCar = carDao.getCarById(expCar.getId());
        assertTrue(actualCar.isIs_rent());

        carDao.changeRentStatus(expCar.getId(), false);
        Car actualFalseCar = carDao.getCarById(expCar.getId());
        assertFalse(actualFalseCar.isIs_rent());
    }

    @Test
    void deleteCarTest() {
        long delCarId = carDao.getCarIdByBrandAndModelForTest("testEdit", "testEdit");
        carDao.delCar(delCarId);
        Car actualCar = carDao.getCarById(delCarId);
        assertNull(actualCar);
    }

    @Test
    public void getCountRecordsFromOrders() {
        int countRecordsFromDb = carDao.getCountRecordsFromCar();
        int exp = 1;
        assertEquals(exp,countRecordsFromDb);
    }

}
