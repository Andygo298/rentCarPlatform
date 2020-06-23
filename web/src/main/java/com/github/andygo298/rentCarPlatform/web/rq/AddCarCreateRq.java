package com.github.andygo298.rentCarPlatform.web.rq;

public class AddCarCreateRq {
    private String brand;
    private String model;
    private String type;
    private String year_mfg;
    private double day_price;

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

    public void setYear_mfg(String year_mfg) {
        this.year_mfg = year_mfg;
    }

    public double getDay_price() {
        return day_price;
    }

    public void setDay_price(double day_price) {
        this.day_price = day_price;
    }
}
