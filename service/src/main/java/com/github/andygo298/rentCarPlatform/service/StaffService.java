package com.github.andygo298.rentCarPlatform.service;

import com.github.andygo298.rentCarPlatform.model.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Staff;

import java.util.List;

public interface StaffService {
    List<Staff> getStaff(int page);
    List<Staff> getStaffWithoutPagination();
    List<Staff> getStaffListByIds(List<Long> staffListIds);
    int getCountRecordsFromStaff();
    Long saveStaff(Staff newStaff);
    void editStaff(EditStaff staffToEdit);
    void delStaff(Long delStaffId);
    Staff getPersonById(Long staffId);
}
