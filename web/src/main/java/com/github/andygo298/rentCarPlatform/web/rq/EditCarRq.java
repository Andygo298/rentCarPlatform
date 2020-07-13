package com.github.andygo298.rentCarPlatform.web.rq;

public class EditCarRq {
    private long carId;
    private String brand;
    private String model;
    private String type;
    private String year_mfg;
    private String img_url;
    private double day_price;

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear_mfg() {
        return year_mfg;
    }

    public void setYear_mfg(String year) {
        this.year_mfg = year;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getDay_price() {
        return day_price;
    }

    public void setDay_price(double day_price) {
        this.day_price = day_price;
    }
}
