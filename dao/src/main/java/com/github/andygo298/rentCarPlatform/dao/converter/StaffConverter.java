package com.github.andygo298.rentCarPlatform.dao.converter;

import com.github.andygo298.rentCarPlatform.dao.entity.StaffEntity;
import com.github.andygo298.rentCarPlatform.model.Staff;

import java.util.stream.Collectors;

public class StaffConverter {
    public static StaffEntity toEntity(Staff staff) {
        if (staff == null) {
            return null;
        }
        final StaffEntity staffEntity = new StaffEntity();
        staffEntity.setId(staff.getId());
        staffEntity.setFirstName(staff.getFirstName());
        staffEntity.setLastName(staff.getLastName());
        staffEntity.setSpecialization(staff.getSpecialization());
        staffEntity.setCar(staff.getCar().stream().map(CarConverter::toEntity).collect(Collectors.toSet()));
        return staffEntity;
    }

    public static Staff fromEntity(StaffEntity staffEntity) {
        if (staffEntity == null) {
            return null;
        }
        return new Staff(
                staffEntity.getId(),
                staffEntity.getFirstName(),
                staffEntity.getLastName(),
                staffEntity.getSpecialization(),
                staffEntity.getCar().stream().map(CarConverter::fromEntity).collect(Collectors.toSet())
        );
    }
}
