package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.dao.impl.DefaultCarDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultStaffDao;
import com.github.andygo298.rentCarPlatform.model.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Specialization;
import com.github.andygo298.rentCarPlatform.model.Staff;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DefaultStaffDaoTest {

    final CarDao carDao = DefaultCarDao.getInstance();
    final StaffDao staffDao = DefaultStaffDao.getInstance();

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
        Session session = SFUtil.getSession();
        session.beginTransaction();
        session.saveOrUpdate(staff1);
        session.saveOrUpdate(staff2);
        session.saveOrUpdate(staff3);
        session.saveOrUpdate(staff4);
        session.saveOrUpdate(staff5);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void getStaffTest() {
        List<Staff> staffFromDb = staffDao.getStaff(0, 10);
        String expFirstName = "Test1";
        String expLastName = "Testov1";
        assertNotNull(staffFromDb);
        assertEquals(expFirstName, staffFromDb.get(2).getFirstName());
        assertEquals(expLastName, staffFromDb.get(2).getLastName());
    }

    @Test
    void getStaffWithoutPaginationTest() {
        List<Staff> staffFromDb = staffDao.getStaffWithoutPagination();
        assertNotNull(staffFromDb);
        assertEquals(7, staffFromDb.size());
    }

    @Test
    void getCountRecordsFromStaffTest() {
        int countRecordsFromStaffFromDb = staffDao.getCountRecordsFromStaff();
        int exp = 6;
        assertEquals(exp, countRecordsFromStaffFromDb);
    }

    @Test
    void getPersonByIdTest() {
        Long expId = 3L;
        Staff personByIdFromDb = staffDao.getPersonById(expId);
        assertNotNull(personByIdFromDb);
        assertEquals("Test1", personByIdFromDb.getFirstName());
        assertEquals("Testov1", personByIdFromDb.getLastName());
    }

    @Test
    void getStaffListByIdsTest() {
        List<Long> longsToDb = Arrays.asList(1L, 3L, 5L);
        List<Staff> staffListByIdsActual = staffDao.getStaffListByIds(longsToDb);
        assertNotNull(staffListByIdsActual);
        assertEquals(3, staffListByIdsActual.size());
        assertEquals("Test1",staffListByIdsActual.get(0).getFirstName());
        assertEquals("Test3",staffListByIdsActual.get(2).getFirstName());
    }
    @Test
    void editStaffTest(){
        Long id = 2L;
        EditStaff editStaff = new EditStaff.EditStaffBuilder(id)
                .withFirstName("EditTest2")
                .withLastName("EditTestov2")
                .withSpecialization(Specialization.DRIVER)
                .withCar(new HashSet<>())
                .build();
        staffDao.editStaff(editStaff);
        Staff personAfterEdit = staffDao.getPersonById(id);
        assertNotNull(personAfterEdit);
        assertEquals(editStaff.getId(), personAfterEdit.getId());
        assertEquals(editStaff.getFirstName(), personAfterEdit.getFirstName());
        assertEquals(editStaff.getLastName(), personAfterEdit.getLastName());
        assertEquals(editStaff.getSpecialization(), personAfterEdit.getSpecialization());
    }
    @Test
    void delStaffTest(){
        Long id = 4L;
        staffDao.delStaff(id);
        Staff personDel = staffDao.getPersonById(id);
        assertNull(personDel);
    }
}
