package com.github.andygo298.rentCarPlatform.model;

import com.github.andygo298.rentCarPlatform.model.enums.Specialization;

import java.util.List;
import java.util.Set;

public class Staff {

    private Long id;
    private String firstName;
    private String lastName;
    private Specialization specialization;

    private List<Car> carSet;

    public Staff(Long id, String firstName, String lastName, Specialization specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public Staff() {
    }

    public Staff(Long id, String firstName, String lastName, Specialization specialization, List<Car> carSet) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.carSet = carSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public List<Car> getCar() {
        return carSet;
    }

    public void setCar(List<Car> carSet) {
        this.carSet = carSet;
    }

//builder class
    public static class StaffBuilder {
        private Staff newStaff;

        public StaffBuilder() {
            newStaff = new Staff();
        }

        public Staff.StaffBuilder withId(Long id){
            newStaff.id = id;
            return this;
        }

        public Staff.StaffBuilder withFirstName(String firstName) {
            newStaff.firstName = firstName;
            return this;
        }

        public Staff.StaffBuilder withLastName(String lastName) {
            newStaff.lastName = lastName;
            return this;
        }

        public Staff.StaffBuilder withSpecialization(Specialization specialization) {
            newStaff.specialization = specialization;
            return this;
        }
        public Staff.StaffBuilder withCar(List<Car> carSet){
            newStaff.carSet = carSet;
            return this;
        }

        public Staff build() {
            return newStaff;
        }
    }
}
