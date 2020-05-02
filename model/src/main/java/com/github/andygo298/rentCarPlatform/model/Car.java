package com.github.andygo298.rentCarPlatform.model;

import javax.persistence.*;
@Entity
@Table(name = "cars")
public class Car {
    private Long id;
    private String brand;
    private String model;
    private String type;
    private String year_mfg;
    private String img_url;
    private double day_price;
    private boolean is_rent;

    public Car() {
        this.is_rent = false;
        this.img_url = "https://avatars.mds.yandex.net/get-pdb/1809111/76fb0b23-8115-44b1-8386-f8e1d3115621/s600";
        this.day_price = 0.00;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "brand", nullable = false, length = 255)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "model", nullable = false, length = 255)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @Basic
    @Column(name = "type", nullable = false, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Basic
    @Column(name = "year_mfg", nullable = false, length = 255)
    public String getYear_mfg() {
        return year_mfg;
    }

    public void setYear_mfg(String year_mfg) {
        this.year_mfg = year_mfg;
    }
    @Basic
    @Column(name = "img_url", nullable = true, length = 255)
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    @Basic
    @Column(name = "day_price", nullable = true, precision = 0)
    public double getDay_price() {
        return day_price;
    }

    public void setDay_price(double day_price) {
        this.day_price = day_price;
    }
    @Basic
    @Column(name = "isRent", nullable = true)
    public boolean isIs_rent() {
        return is_rent;
    }

    public void setIs_rent(boolean is_rent) {
        this.is_rent = is_rent;
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
        private Car newCar;

        public CarBuilder(Long id) {
            newCar = new Car();
            newCar.id = id;
        }

        public CarBuilder withBrand(String brand) {
            newCar.brand = brand;
            return this;
        }

        public CarBuilder withModel(String model) {
            newCar.model = model;
            return this;
        }

        public CarBuilder withType(String type) {
            newCar.type = type;
            return this;
        }

        public CarBuilder withYear(String year) {
            newCar.year_mfg = year;
            return this;
        }

        public CarBuilder withPrice(double price) {
            newCar.day_price = price;
            return this;
        }

        public CarBuilder withImg(String img) {
            newCar.img_url = img;
            return this;
        }

        public CarBuilder withIsRent(boolean status) {
            newCar.is_rent = status;
            return this;
        }

        public Car build() {
            return newCar;
        }
    }
}
