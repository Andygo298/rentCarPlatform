package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.dao.StaffDao;
import com.github.andygo298.rentCarPlatform.model.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Staff;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.TypedQuery;
import java.util.List;

public class DefaultStaffDao implements StaffDao {

    private static final Logger log = LoggerFactory.getLogger(DefaultStaffDao.class);

    private static class SingletonHolder {
        static final StaffDao HOLDER_INSTANCE = new DefaultStaffDao();
    }

    public static StaffDao getInstance() {
        return DefaultStaffDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Staff> getStaff(int skipRecords, int limitRecords) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<Staff> query = session.createQuery("from Staff ", Staff.class)
                    .setFirstResult(skipRecords)
                    .setMaxResults(limitRecords);
            List<Staff> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList;
        }
    }

    @Override
    public List<Staff> getStaffWithoutPagination() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<Staff> query = session.createQuery("from Staff ", Staff.class);
            List<Staff> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList;
        }

    }

    @Override
    public Staff getPersonById(Long staffId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Staff getPerson = session.get(Staff.class, staffId);
            session.getTransaction().commit();
            return getPerson;
        }
    }

    @Override
    public List<Staff> getStaffListByIds(List<Long> staffListIds) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<Staff> query = session.createQuery("from Staff s where s.id in (:staffIds)", Staff.class)
                    .setParameterList("staffIds",staffListIds);
            List<Staff> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList;
        }
    }

    @Override
    public int getCountRecordsFromStaff() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<Staff> query = session.createQuery("from Staff ", Staff.class);
            int resultCount = query.getResultList().size();
            session.getTransaction().commit();
            session.close();
            return resultCount;
        }
    }

    @Override
    public Long saveStaff(Staff newStaff) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(newStaff);
            long id = newStaff.getId();
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    @Override
    public void editStaff(EditStaff staffToEdit) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Staff staff = session.get(Staff.class, staffToEdit.getId());
            staff.setFirstName(staffToEdit.getFirstName());
            staff.setLastName(staffToEdit.getLastName());
            staff.setSpecialization(staffToEdit.getSpecialization());
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delStaff(Long delStaffId) {

    }
}
