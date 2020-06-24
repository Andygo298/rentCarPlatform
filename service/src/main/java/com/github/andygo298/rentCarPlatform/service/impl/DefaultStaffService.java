package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.utils.Constant;
import com.github.andygo298.rentCarPlatform.dao.StaffDao;
import com.github.andygo298.rentCarPlatform.model.actions.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import com.github.andygo298.rentCarPlatform.service.StaffService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultStaffService implements StaffService {
    private final StaffDao staffDao;

    public DefaultStaffService(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    @Override
    @Transactional
    public List<Staff> getStaff(int page) {
        return staffDao.getStaff(ServiceUtil.getSkipRecords(page), Constant.LIMIT_RECORDS);
    }

    @Override
    @Transactional
    public List<Staff> getStaffWithoutPagination() {
        return staffDao.getStaffWithoutPagination();
    }

    @Override
    @Transactional
    public Staff getPersonById(Long staffId) {
        return staffDao.getPersonById(staffId);
    }

    @Override
    @Transactional
    public List<Staff> getStaffListByIds(List<Long> staffListIds) {
       return staffDao.getStaffListByIds(staffListIds);
    }

    @Override
    @Transactional
    public int getCountRecordsFromStaff() {
        return staffDao.getCountRecordsFromStaff();
    }

    @Override
    @Transactional
    public Long saveStaff(Staff newStaff) {
       return staffDao.saveStaff(newStaff);
    }

    @Override
    @Transactional
    public void editStaff(EditStaff staffToEdit) {
        staffDao.editStaff(staffToEdit);
    }

    @Override
    @Transactional
    public void delStaff(Long delStaffId) {
        staffDao.delStaff(delStaffId);
    }

    @Override
    @Transactional
    public void removeStaffFromCar(Long remCarId, Long remStaffId) {
        staffDao.removeStaffFromCar(remCarId,remStaffId);
    }
}
