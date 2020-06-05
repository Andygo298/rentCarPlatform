package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.dao.StaffDao;
import com.github.andygo298.rentCarPlatform.dao.converter.StaffConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.CarEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.StaffEntity;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.actions.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Staff;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            TypedQuery<StaffEntity> query = session.createQuery("from StaffEntity ", StaffEntity.class)
                    .setFirstResult(skipRecords)
                    .setMaxResults(limitRecords);
            List<StaffEntity> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList
                    .stream()
                    .map(StaffConverter::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Staff> getStaffWithoutPagination() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<StaffEntity> query = session.createQuery("from StaffEntity ", StaffEntity.class);
            List<StaffEntity> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList
                    .stream()
                    .map(StaffConverter::fromEntity)
                    .collect(Collectors.toList());
        }

    }

    @Override
    public Staff getPersonById(Long staffId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            StaffEntity getPerson = session.get(StaffEntity.class, staffId);
            session.getTransaction().commit();
            return StaffConverter.fromEntity(getPerson);
        }
    }

    @Override
    public List<Staff> getStaffListByIds(List<Long> staffListIds) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<StaffEntity> query = session.createQuery("from StaffEntity s where s.id in (:staffIds)", StaffEntity.class)
                    .setParameterList("staffIds", staffListIds);
            List<StaffEntity> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList
                    .stream()
                    .map(StaffConverter::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public int getCountRecordsFromStaff() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<StaffEntity> query = session.createQuery("from StaffEntity ", StaffEntity.class);
            int resultCount = query.getResultList().size();
            session.getTransaction().commit();
            session.close();
            return resultCount;
        }
    }

    @Override
    public Long saveStaff(Staff newStaff) {
        StaffEntity staffEntity = StaffConverter.toEntity(newStaff);
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(staffEntity);
            long id = staffEntity.getId();
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    @Override
    public void editStaff(EditStaff staffToEdit) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            StaffEntity staffEntity = session.get(StaffEntity.class, staffToEdit.getId());
            staffEntity.setFirstName(staffToEdit.getFirstName());
            staffEntity.setLastName(staffToEdit.getLastName());
            staffEntity.setSpecialization(staffToEdit.getSpecialization());
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delStaff(Long delStaffId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            StaffEntity delStaff = session.get(StaffEntity.class, delStaffId);
            Set<CarEntity> carEntitySet = delStaff.getCarEntitySet();
            carEntitySet.forEach(e -> e.setStaff(new HashSet<>()));
            delStaff.setCarEntitySet(new HashSet<>());
            session.getTransaction().commit();
            session.beginTransaction();
            session.delete(delStaff);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void removeStaffFromCar(Long remCarId, Long remStaffId) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            CarEntity car = session.get(CarEntity.class, remCarId);
            StaffEntity staff = session.get(StaffEntity.class, remStaffId);

            StaffEntity staffToRem = car.getStaff()
                    .stream()
                    .filter(person -> person.getId().equals(staff.getId()))
                    .findFirst()
                    .orElse(null);
            car.getStaff().remove(staffToRem);

            CarEntity carToRem = staff.getCarEntitySet()
                    .stream()
                    .filter(carRem -> carRem.equals(car))
                    .findFirst()
                    .orElse(null);
            staff.getCarEntitySet().remove(carToRem);

            session.getTransaction().commit();
            session.close();
        }
    }
}
