package com.github.andygo298.rentCarPlatform.dao.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Cars {
    private Long id;
    private String brand;
    private String model;
    private String type;
    private String yearMfg;
    private String imgUrl;
    private Double dayPrice;
    private Boolean isRent;

    @Id
    @Column(name = "id", nullable = false)
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
    public String getYearMfg() {
        return yearMfg;
    }

    public void setYearMfg(String yearMfg) {
        this.yearMfg = yearMfg;
    }

    @Basic
    @Column(name = "img_url", nullable = true, length = 255)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Basic
    @Column(name = "day_price", nullable = true, precision = 0)
    public Double getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(Double dayPrice) {
        this.dayPrice = dayPrice;
    }

    @Basic
    @Column(name = "isRent", nullable = true)
    public Boolean getRent() {
        return isRent;
    }

    public void setRent(Boolean rent) {
        isRent = rent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cars cars = (Cars) o;
        return Objects.equals(id, cars.id) &&
                Objects.equals(brand, cars.brand) &&
                Objects.equals(model, cars.model) &&
                Objects.equals(type, cars.type) &&
                Objects.equals(yearMfg, cars.yearMfg) &&
                Objects.equals(imgUrl, cars.imgUrl) &&
                Objects.equals(dayPrice, cars.dayPrice) &&
                Objects.equals(isRent, cars.isRent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, type, yearMfg, imgUrl, dayPrice, isRent);
    }
}
