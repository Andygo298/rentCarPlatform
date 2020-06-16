package Impl;

import com.github.andygo298.rentCarPlatform.dao.CarDao;
import com.github.andygo298.rentCarPlatform.dao.StaffDao;
import com.github.andygo298.rentCarPlatform.dao.config.DaoConfig;
import com.github.andygo298.rentCarPlatform.model.actions.EditStaff;
import com.github.andygo298.rentCarPlatform.model.enums.Specialization;
import com.github.andygo298.rentCarPlatform.model.Staff;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultStaffEntityDaoTest {

    @Autowired
    private CarDao carDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private SessionFactory sessionFactory;

/*
    @BeforeAll
    static void init() {
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
        Staff staff3 = new Staff.StaffBuilder()
                .withFirstName("Test3")
                .withLastName("Testov3")
                .withSpecialization(Specialization.CLEANER)
                .build();
        Staff staff4 = new Staff.StaffBuilder()
                .withFirstName("Test4")
                .withLastName("Testov4")
                .withSpecialization(Specialization.MECHANIC)
                .build();
        Staff staff5 = new Staff.StaffBuilder()
                .withFirstName("Test5")
                .withLastName("Testov5")
                .withSpecialization(Specialization.MECHANIC)
                .build();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(staff1);
        session.saveOrUpdate(staff2);
        session.saveOrUpdate(staff3);
        session.saveOrUpdate(staff4);
        session.saveOrUpdate(staff5);
        session.getTransaction().commit();
        session.close();
    }
*/

    @Test
    void getStaffTest() {
        Staff staff1 = new Staff.StaffBuilder()
                .withFirstName("Test1")
                .withLastName("Testov1")
                .withSpecialization(Specialization.CLEANER)
                .withCar(new ArrayList<>())
                .build();
        Staff staff2 = new Staff.StaffBuilder()
                .withFirstName("Test2")
                .withLastName("Testov2")
                .withSpecialization(Specialization.DRIVER)
                .withCar(new ArrayList<>())
                .build();
        Long aLong1 = staffDao.saveStaff(staff1);
        Long aLong2 = staffDao.saveStaff(staff2);
        List<Staff> staffFromDb = staffDao.getStaff(0, 10);
        String expFirstName = "t11";
        String expLastName = "t11";
        assertNotNull(staffFromDb);
        assertEquals(expFirstName, staffFromDb.get(0).getFirstName());
        assertEquals(expLastName, staffFromDb.get(0).getLastName());
    }

    @Test
    void getStaffWithoutPaginationTest() {
        List<Staff> staffFromDb = staffDao.getStaffWithoutPagination();
        assertNotNull(staffFromDb);
        assertEquals(2, staffFromDb.size());
    }

    @Test
    void getCountRecordsFromStaffTest() {
        int countRecordsFromStaffFromDb = staffDao.getCountRecordsFromStaff();
        int exp = 3;
        assertEquals(exp, countRecordsFromStaffFromDb);
    }

    @Test
    void getPersonByIdTest() {
        Long expId = 1L;
        Staff personByIdFromDb = staffDao.getPersonById(expId);
        assertNotNull(personByIdFromDb);
        assertEquals("t11", personByIdFromDb.getFirstName());
        assertEquals("t11", personByIdFromDb.getLastName());
    }

    @Test
    void getStaffListByIdsTest() {
        List<Long> longsToDb = Arrays.asList(1L, 2L);
        List<Staff> staffListByIdsActual = staffDao.getStaffListByIds(longsToDb);
        assertNotNull(staffListByIdsActual);
        assertEquals(2, staffListByIdsActual.size());
        assertEquals("t11", staffListByIdsActual.get(0).getFirstName());
        assertEquals("t22", staffListByIdsActual.get(1).getFirstName());
    }

    @Test
    void editStaffTest() {
        Staff staff = new Staff.StaffBuilder()
                .withFirstName("TestEdit")
                .withLastName("TestovEdit")
                .withSpecialization(Specialization.CLEANER)
                .withCar(new ArrayList<>())
                .build();
        Long aLong = staffDao.saveStaff(staff);
        EditStaff editStaff = new EditStaff.EditStaffBuilder(aLong)
                .withFirstName("EditTest2")
                .withLastName("EditTestov2")
                .withSpecialization(Specialization.DRIVER)
                .withCar(new HashSet<>())
                .build();
        staffDao.editStaff(editStaff);
        Staff personAfterEdit = staffDao.getPersonById(aLong);
        assertNotNull(personAfterEdit);
        assertEquals(editStaff.getId(), personAfterEdit.getId());
        assertEquals(editStaff.getFirstName(), personAfterEdit.getFirstName());
        assertEquals(editStaff.getLastName(), personAfterEdit.getLastName());
        assertEquals(editStaff.getSpecialization(), personAfterEdit.getSpecialization());
    }

    @Test
    void delStaffTest() {
        Staff staffDel = new Staff.StaffBuilder()
                .withFirstName("TestDel")
                .withLastName("TestovDel")
                .withSpecialization(Specialization.CLEANER)
                .withCar(new ArrayList<>())
                .build();
        Long aLong = staffDao.saveStaff(staffDel);
        staffDao.delStaff(aLong);
        Staff personDel = staffDao.getPersonById(aLong);
        assertNull(personDel);
    }
}
