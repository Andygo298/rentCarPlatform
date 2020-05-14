package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Staff;

import java.util.List;

public interface StaffDao {
    List<Staff> getStaff(int skipRecords, int limitRecords);
    List<Staff> getStaffWithoutPagination();
    int getCountRecordsFromStaff();
    Long saveStaff(Staff newStaff);
    void editStaff(EditStaff staffToEdit);
    void delStaff(Long delStaffId);
    Staff getPersonById(Long staffId);
    List<Staff> getStaffListByIds(List<Long> staffListIds);
    void removeStaffFromCar(Long remCarId, Long remStaffId);
}
