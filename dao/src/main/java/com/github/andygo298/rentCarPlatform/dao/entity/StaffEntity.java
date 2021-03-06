package com.github.andygo298.rentCarPlatform.dao.entity;

import com.github.andygo298.rentCarPlatform.model.enums.Specialization;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "staff")
public class StaffEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private Specialization specialization;

    private List<CarEntity> carEntitySet = new ArrayList<>();

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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "staff", fetch = FetchType.EAGER )
    public List<CarEntity> getCarEntitySet() {
        return carEntitySet;
    }

    public void setCarEntitySet(List<CarEntity> carEntitySet) {
        this.carEntitySet = carEntitySet;
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
        public StaffEntity.StaffBuilder withCar(List<CarEntity> carEntity){
            newStaffEntity.carEntitySet = carEntity;
            return this;
        }

        public StaffEntity build() {
            return newStaffEntity;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffEntity that = (StaffEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
