package com.github.andygo298.rentCarPlatform.model;

import com.github.andygo298.rentCarPlatform.model.enums.Specialization;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "staff")
public class Staff {
    private Long id;
    private String firstName;
    private String lastName;
    private Specialization specialization;

    private Set<Car> carSet = new HashSet<>();

    public Staff(Long id, String firstName, String lastName, Specialization specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public Staff() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "specialization", nullable = false)
    @Enumerated(EnumType.STRING)
    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "staff_cars", joinColumns = {@JoinColumn(name = "staff_id")},
            inverseJoinColumns = {@JoinColumn(name = "car_id")})
    public Set<Car> getCar() {
        return carSet;
    }

    public void setCar(Set<Car> car) {
        this.carSet = car;
    }

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
        public Staff.StaffBuilder withCar(Set<Car> car){
            newStaff.carSet = car;
            return this;
        }

        public Staff build() {
            return newStaff;
        }
    }
}
