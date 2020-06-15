package com.github.andygo298.rentCarPlatform.model.actions;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.enums.Specialization;

import java.util.HashSet;
import java.util.Set;

public class EditStaff {
    private Long id;
    private String firstName;
    private String lastName;
    private Specialization specialization;

    private Set<Car> carSet = new HashSet<>();

    public EditStaff() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public Set<Car> getCarSet() {
        return carSet;
    }
    public static class EditStaffBuilder {
        private EditStaff newEditStaff;

        public EditStaffBuilder(Long id) {
            newEditStaff = new EditStaff();
            newEditStaff.id = id;
        }

        public EditStaff.EditStaffBuilder withFirstName(String firstName) {
            newEditStaff.firstName = firstName;
            return this;
        }

        public EditStaff.EditStaffBuilder withLastName(String lastName) {
            newEditStaff.lastName = lastName;
            return this;
        }

        public EditStaff.EditStaffBuilder withSpecialization(Specialization specialization) {
            newEditStaff.specialization = specialization;
            return this;
        }
        public EditStaff.EditStaffBuilder withCar(Set<Car> car){
            newEditStaff.carSet = car;
            return this;
        }

        public EditStaff build() {
            return newEditStaff;
        }
    }

}
