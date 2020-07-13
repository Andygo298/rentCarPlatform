package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.StaffDao;
import com.github.andygo298.rentCarPlatform.dao.converter.StaffConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.CarEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.StaffEntity;
import com.github.andygo298.rentCarPlatform.model.actions.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Staff;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultStaffDao implements StaffDao {
    private final SessionFactory sessionFactory;

    public DefaultStaffDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Staff> getStaff(int skipRecords, int limitRecords) {
        TypedQuery<StaffEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from StaffEntity ", StaffEntity.class)
                .setFirstResult(skipRecords)
                .setMaxResults(limitRecords);
        List<StaffEntity> resultList = query.getResultList();
        return resultList
                .stream()
                .map(StaffConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Staff> getStaffWithoutPagination() {
        TypedQuery<StaffEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from StaffEntity ", StaffEntity.class);
        List<StaffEntity> resultList = query.getResultList();
        return resultList
                .stream()
                .map(StaffConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Staff getPersonById(Long staffId) {
        StaffEntity getPerson = sessionFactory.getCurrentSession().get(StaffEntity.class, staffId);
        return StaffConverter.fromEntity(getPerson);
    }

    @Override
    public List<Staff> getStaffListByIds(List<Long> staffListIds) {
        TypedQuery<StaffEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from StaffEntity s where s.id in (:staffIds)", StaffEntity.class)
                .setParameterList("staffIds", staffListIds);
        List<StaffEntity> resultList = query.getResultList();
        return resultList
                .stream()
                .map(StaffConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public int getCountRecordsFromStaff() {
        TypedQuery<StaffEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from StaffEntity ", StaffEntity.class);
        return query.getResultList().size();
    }

    @Override
    public Long saveStaff(Staff newStaff) {
        StaffEntity staffEntity = StaffConverter.toEntity(newStaff);
        sessionFactory.getCurrentSession().saveOrUpdate(staffEntity);
        return staffEntity.getId();
    }

    @Override
    public void editStaff(EditStaff staffToEdit) {
        Session session = sessionFactory.getCurrentSession();
        StaffEntity staffEntity = session.get(StaffEntity.class, staffToEdit.getId());
        staffEntity.setFirstName(staffToEdit.getFirstName());
        staffEntity.setLastName(staffToEdit.getLastName());
        staffEntity.setSpecialization(staffToEdit.getSpecialization());
        session.update(staffEntity);
    }

    @Override
    public void delStaff(Long delStaffId) {
        Session session = sessionFactory.getCurrentSession();
        StaffEntity delStaff = session.get(StaffEntity.class, delStaffId);
        for (CarEntity carEntity : delStaff.getCarEntitySet()) {
            carEntity.getStaff().forEach(e -> e.setCarEntitySet(null));
        }
        session.delete(delStaff);
    }

    @Override
    public void removeStaffFromCar(Long remCarId, Long remStaffId) {
        Session session = sessionFactory.getCurrentSession();
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
    }
}
