package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.Constant;
import com.github.andygo298.rentCarPlatform.dao.StaffDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultStaffDao;
import com.github.andygo298.rentCarPlatform.model.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import com.github.andygo298.rentCarPlatform.service.StaffService;

import java.util.List;

public class DefaultStaffService implements StaffService {

    private static class SingletonHolder {
        static final StaffService HOLDER_INSTANCE = new DefaultStaffService();
    }

    public static StaffService getInstance() {
        return DefaultStaffService.SingletonHolder.HOLDER_INSTANCE;
    }

    private StaffDao staffDao = DefaultStaffDao.getInstance();

    @Override
    public List<Staff> getStaff(int page) {
        return staffDao.getStaff(ServiceUtil.getSkipRecords(page), Constant.LIMIT_RECORDS);
    }

    @Override
    public List<Staff> getStaffWithoutPagination() {
        return staffDao.getStaffWithoutPagination();
    }

    @Override
    public Staff getPersonById(Long staffId) {
        return staffDao.getPersonById(staffId);
    }

    @Override
    public List<Staff> getStaffListByIds(List<Long> staffListIds) {
       return staffDao.getStaffListByIds(staffListIds);
    }

    @Override
    public int getCountRecordsFromStaff() {
        return staffDao.getCountRecordsFromStaff();
    }

    @Override
    public Long saveStaff(Staff newStaff) {
       return staffDao.saveStaff(newStaff);
    }

    @Override
    public void editStaff(EditStaff staffToEdit) {
        staffDao.editStaff(staffToEdit);
    }

    @Override
    public void delStaff(Long delStaffId) {
        staffDao.delStaff(delStaffId);
    }
}
