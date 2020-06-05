package com.github.andygo298.rentCarPlatform.dao.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cars")
public class CarEntity {
    private Long id;
    private String brand;
    private String model;
    private String type;
    private String year_mfg;
    private String img_url;
    private double day_price;
    private boolean is_rent;

    private OrderEntity orderEntity;
    private Set<StaffEntity> staffEntitySet = new HashSet<>();

    public CarEntity() {
        this.is_rent = false;
        this.img_url = "https://avatars.mds.yandex.net/get-pdb/1809111/76fb0b23-8115-44b1-8386-f8e1d3115621/s600";
        this.day_price = 0.00;
    }

    public CarEntity(Long id, String brand, String model, String type, String year_mfg, String img_url, double day_price, boolean is_rent, OrderEntity orderEntity, Set<StaffEntity> staffEntitySet) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.year_mfg = year_mfg;
        this.img_url = img_url;
        this.day_price = day_price;
        this.is_rent = is_rent;
        this.orderEntity = orderEntity;
        this.staffEntitySet = staffEntitySet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "brand", nullable = false)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "model", nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "year_mfg", nullable = false)
    public String getYear_mfg() {
        return year_mfg;
    }

    public void setYear_mfg(String year_mfg) {
        this.year_mfg = year_mfg;
    }

    @Column(name = "img_url")
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Column(name = "day_price")
    public double getDay_price() {
        return day_price;
    }

    public void setDay_price(double day_price) {
        this.day_price = day_price;
    }

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "isRent", nullable = false)
    public boolean isIs_rent() {
        return is_rent;
    }

    public void setIs_rent(boolean is_rent) {
        this.is_rent = is_rent;
    }

    @OneToOne(mappedBy = "carEntity", fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    @ManyToMany(mappedBy = "carEntitySet",cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    public Set<StaffEntity> getStaff() {
        return staffEntitySet;
    }

    public void setStaff(Set<StaffEntity> staffEntities) {
        this.staffEntitySet = staffEntities;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", year_mfg='" + year_mfg + '\'' +
                ", img_url='" + img_url + '\'' +
                ", day_price=" + day_price +
                ", is_rent=" + is_rent +
                '}';
    }

    public static class CarBuilder {
        private CarEntity newCarEntity;

        public CarBuilder(Long id) {
            newCarEntity = new CarEntity();
            newCarEntity.id = id;
        }

        public CarBuilder withBrand(String brand) {
            newCarEntity.brand = brand;
            return this;
        }

        public CarBuilder withModel(String model) {
            newCarEntity.model = model;
            return this;
        }

        public CarBuilder withType(String type) {
            newCarEntity.type = type;
            return this;
        }

        public CarBuilder withYear(String year) {
            newCarEntity.year_mfg = year;
            return this;
        }

        public CarBuilder withPrice(double price) {
            newCarEntity.day_price = price;
            return this;
        }

        public CarBuilder withImg(String img) {
            newCarEntity.img_url = img;
            return this;
        }

        public CarBuilder withIsRent(boolean status) {
            newCarEntity.is_rent = status;
            return this;
        }

        public CarEntity build() {
            return newCarEntity;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity carEntity = (CarEntity) o;
        return Objects.equals(id, carEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
