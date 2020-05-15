package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.Constant;
import com.github.andygo298.rentCarPlatform.dao.StaffDao;
import com.github.andygo298.rentCarPlatform.model.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Specialization;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.StaffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DefaultStaffServiceTest {

    @Mock
    StaffDao staffDao;

    @InjectMocks
    DefaultStaffService defaultStaffService;

    @Test
    void getInstanceTest() {
        StaffService instance = DefaultStaffService.getInstance();
        assertNotNull(instance);
    }

    @Test
    void getStaffTest(){
        Staff staff4 = new Staff.StaffBuilder()
                .withFirstName("Test4")
                .withLastName("Testov4")
                .withSpecialization(Specialization.MECHANIC)
                .build();
        Staff staff5 = new Staff.StaffBuilder()
                .withFirstName("Test5")
                .withLastName("Testov5")
                .withSpecialization(Specialization.CLEANER)
                .build();
        List<Staff> staffList = Arrays.asList(staff4, staff5);
        when(staffDao.getStaff(0, Constant.LIMIT_RECORDS)).thenReturn(staffList);
        List<Staff> staffFromDb = defaultStaffService.getStaff(1);
        assertNotNull(staffFromDb);
        assertEquals(staffList,staffFromDb);
    }
    @Test
    void getStaffWithoutPaginationTest(){
        Staff staff4 = new Staff.StaffBuilder()
                .withFirstName("Test4")
                .withLastName("Testov4")
                .withSpecialization(Specialization.MECHANIC)
                .build();
        Staff staff5 = new Staff.StaffBuilder()
                .withFirstName("Test5")
                .withLastName("Testov5")
                .withSpecialization(Specialization.CLEANER)
                .build();
        Staff staff1 = new Staff.StaffBuilder()
                .withFirstName("Test1")
                .withLastName("Testov1")
                .withSpecialization(Specialization.MECHANIC)
                .build();
        Staff staff2 = new Staff.StaffBuilder()
                .withFirstName("Test2")
                .withLastName("Testov2")
                .withSpecialization(Specialization.CLEANER)
                .build();
        List<Staff> staffList = Arrays.asList(staff1, staff2, staff4, staff5);
        when(staffDao.getStaffWithoutPagination()).thenReturn(staffList);
        List<Staff> staffWithoutPaginationFromDb = defaultStaffService.getStaffWithoutPagination();
        assertNotNull(staffWithoutPaginationFromDb);
        assertEquals(staffList,staffWithoutPaginationFromDb);
    }

    @Test
    void getStaffListByIdsTest(){
        List<Long> longIds = Arrays.asList(1L, 3L);
        Staff staff1 = new Staff.StaffBuilder()
                .withId(1L)
                .withFirstName("Test1")
                .withLastName("Testov1")
                .withSpecialization(Specialization.MECHANIC)
                .build();
        Staff staff2 = new Staff.StaffBuilder()
                .withId(3L)
                .withFirstName("Test3")
                .withLastName("Testov3")
                .withSpecialization(Specialization.CLEANER)
                .build();
        List<Staff> staff = Arrays.asList(staff1, staff2);
        when(staffDao.getStaffListByIds(longIds)).thenReturn(staff);
        List<Staff> staffListByIdsFromDb = defaultStaffService.getStaffListByIds(longIds);
        assertNotNull(staffListByIdsFromDb);
        assertEquals(staff,staffListByIdsFromDb);
    }
    @Test
    void getCountRecordsFromStaffTest(){
        int exp = 2;
        when(staffDao.getCountRecordsFromStaff()).thenReturn(exp);
        int countRecordsFromStaffFromDb = defaultStaffService.getCountRecordsFromStaff();
        assertEquals(exp,countRecordsFromStaffFromDb);
    }
    @Test
    void saveStaffTest(){
        Staff staff1 = new Staff.StaffBuilder()
                .withId(1L)
                .withFirstName("Test1")
                .withLastName("Testov1")
                .withSpecialization(Specialization.MECHANIC)
                .build();
        when(staffDao.saveStaff(staff1)).thenReturn(1L);
        Long longActual = defaultStaffService.saveStaff(staff1);
        assertNotNull(longActual);
        assertEquals((Object) 1L,longActual);
    }
    @Test
    void editStaffTest(){
        EditStaff editStaff = new EditStaff.EditStaffBuilder(2L)
                .withFirstName("EditTest2")
                .withLastName("EditTestov2")
                .withSpecialization(Specialization.DRIVER)
                .withCar(new HashSet<>())
                .build();
        doNothing().when(staffDao).editStaff(editStaff);
        defaultStaffService.editStaff(editStaff);
        verify(staffDao, times(1)).editStaff(editStaff);
    }
    @Test
    void delStaffTest(){
        doNothing().when(staffDao).delStaff(anyLong());
        defaultStaffService.delStaff(3L);
        defaultStaffService.delStaff(5L);
        verify(staffDao,times(2)).delStaff(anyLong());
    }
    @Test
    void removeStaffFromCarTest(){
        doNothing().when(staffDao).removeStaffFromCar(anyLong(),anyLong());
        defaultStaffService.removeStaffFromCar(3L,10L);
        defaultStaffService.removeStaffFromCar(8L,5L);
        defaultStaffService.removeStaffFromCar(1L,3L);
        verify(staffDao,times(3)).removeStaffFromCar(anyLong(),anyLong());
    }
    @Test
    void getPersonByIdTest(){
        Staff staff1 = new Staff.StaffBuilder()
                .withId(2L)
                .withFirstName("Test1")
                .withLastName("Testov1")
                .withSpecialization(Specialization.MECHANIC)
                .build();
        when(staffDao.getPersonById(anyLong())).thenReturn(staff1);
        Staff personByIdFromDb = defaultStaffService.getPersonById(2L);
        assertNotNull(personByIdFromDb);
        assertEquals(staff1,personByIdFromDb);
    }
}
