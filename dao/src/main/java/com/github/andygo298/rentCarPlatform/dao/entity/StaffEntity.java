package com.github.andygo298.rentCarPlatform.dao.entity;

import com.github.andygo298.rentCarPlatform.model.enums.Specialization;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "staff")
public class StaffEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private Specialization specialization;

    private Set<CarEntity> carEntitySet = new HashSet<>();

    public StaffEntity(Long id, String firstName, String lastName, Specialization specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public StaffEntity() {
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
    public Set<CarEntity> getCar() {
        return carEntitySet;
    }

    public void setCar(Set<CarEntity> carEntity) {
        this.carEntitySet = carEntity;
    }

    public static class StaffBuilder {
        private StaffEntity newStaffEntity;

        public StaffBuilder() {
            newStaffEntity = new StaffEntity();
        }

        public StaffEntity.StaffBuilder withId(Long id){
            newStaffEntity.id = id;
            return this;
        }

        public StaffEntity.StaffBuilder withFirstName(String firstName) {
            newStaffEntity.firstName = firstName;
            return this;
        }

        public StaffEntity.StaffBuilder withLastName(String lastName) {
            newStaffEntity.lastName = lastName;
            return this;
        }

        public StaffEntity.StaffBuilder withSpecialization(Specialization specialization) {
            newStaffEntity.specialization = specialization;
            return this;
        }
        public StaffEntity.StaffBuilder withCar(Set<CarEntity> carEntity){
            newStaffEntity.carEntitySet = carEntity;
            return this;
        }

        public StaffEntity build() {
            return newStaffEntity;
        }
    }
}
