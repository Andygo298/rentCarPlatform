package com.github.andygo298.rentCarPlatform.web.rq;

public class AddCarCreateRq {
    private String brand;
    private String model;
    private String type;
    private String year_mfg;
    private double dayPrice;

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

    public double getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(double dayPrice) {
        this.dayPrice = dayPrice;
    }
}
