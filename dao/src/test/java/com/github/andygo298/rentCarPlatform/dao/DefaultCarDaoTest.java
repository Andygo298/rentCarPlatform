package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.dao.impl.DefaultCarDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultStaffDao;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;
import com.github.andygo298.rentCarPlatform.model.Specialization;
import com.github.andygo298.rentCarPlatform.model.Staff;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCarDaoTest {

    final CarDao carDao = DefaultCarDao.getInstance();
    final StaffDao staffDao = DefaultStaffDao.getInstance();

    @BeforeAll
    public static void init() {
        Session session = SFUtil.getSession();
        session.beginTransaction();
        Car car1 = new Car.CarBuilder(null)
                .withBrand("Renault")
                .withModel("Duster")
                .withYear("2015")
                .withType("SUV")
                .withPrice(65)
                .build();
        Car car2 = new Car.CarBuilder(null)
                .withBrand("Mersedes-Benz")
                .withModel("GLK-240")
                .withYear("2010")
                .withType("SUV")
                .withPrice(100)
                .build();
        Car car3 = new Car.CarBuilder(null)
                .withBrand("Opel")
                .withModel("Vectra")
                .withYear("2001")
                .withType("Sedan")
                .withPrice(45)
                .build();

        session.saveOrUpdate(car1);
        session.saveOrUpdate(car2);
        session.saveOrUpdate(car3);
        session.getTransaction().commit();
        session.close();
    }

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
                .withBrand("Renault")
                .withModel("Arkana")
                .withType("SUV")
                .withYear("2019")
                .withImg("google.com")
                .withPrice(78)
                .build();
        Staff staff1 = new Staff.StaffBuilder()
                .withFirstName("Test1")
                .withLastName("Testov1")
                .withSpecialization(Specialization.CLEANER)
                .build();
        Staff staff2 = new Staff.StaffBuilder()
                .withFirstName("Test2")
                .withLastName("Testov2")
                .withSpecialization(Specialization.DRIVER)
                .build();
        carDao.saveCar(car);
        staffDao.saveStaff(staff1);
        staffDao.saveStaff(staff2);

        List<Staff> staffList = Arrays.asList(staff1, staff2);
        carDao.saveStaffIntoCar(car,staffList);
        long idCarActual = carDao.getCarIdByBrandAndModelForTest(car.getBrand(), car.getModel());
        Car expCar = carDao.getCarById(idCarActual);
        Set<Staff> staff = expCar.getStaff();
        assertNotNull(staff);
        Iterator<Staff> iterator = staff.iterator();
        assertEquals(staff.size(),staffList.size());
    }

    @Test
    void testGetCarByIdTest() {
        final Long carId = 2L;
        String expModel = "GLK-240";
        String expYear = "2010";
        final Car actualCar = carDao.getCarById(carId);
        assertEquals(carId, actualCar.getId());
        assertEquals(expModel, actualCar.getModel());
        assertEquals(expYear, actualCar.getYear_mfg());
    }

    @Test
    void editCarTest() {
        long idCar = 3L;
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
        int exp = 3;
        assertEquals(exp, countRecordsFromDb);
    }

    @Test
    void changeRentStatusTest() {
        long id = 3L;
        carDao.changeRentStatus(id, true);
        Car actualCar = carDao.getCarById(id);
        assertTrue(actualCar.isIs_rent());

        carDao.changeRentStatus(id, false);
        Car actualFalseCar = carDao.getCarById(id);
        assertFalse(actualFalseCar.isIs_rent());
    }

    @Test
    void deleteCarTest() {
        long delCarId = carDao.getCarIdByBrandAndModelForTest("Renault", "Duster");
        carDao.delCar(delCarId);
        Car actualCar = carDao.getCarById(delCarId);
        assertNull(actualCar);
    }

    @Test
    public void getCountRecordsFromOrders() {
        int countRecordsFromDb = carDao.getCountRecordsFromCar();
        int exp = 3;
        assertEquals(exp,countRecordsFromDb);
    }

    @AfterAll
    static void afterAll() {
        Session session = SFUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete from Car c where c.id=:id").setParameter("id",2L).executeUpdate();
        session.createQuery("delete from Car c where c.id=:id").setParameter("id",3L).executeUpdate();
        session.createQuery("delete from Car c where c.id=:id").setParameter("id",4L).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

}
