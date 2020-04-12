package com.github.andygo298.rentCarPlatform.model;

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

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public String getYear_mfg() {
        return year_mfg;
    }

    public String getImg_url() {
        return img_url;
    }

    public double getDay_price() {
        return day_price;
    }

    public boolean isIs_rent() {
        return is_rent;
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
