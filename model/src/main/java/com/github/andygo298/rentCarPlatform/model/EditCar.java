package com.github.andygo298.rentCarPlatform.model;

public class EditCar {
    private Long id;
    private String brand;
    private String model;
    private String type;
    private String year_mfg;
    private String img_url;
    private double day_price;

    public EditCar() {
    }

    //getters
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

    @Override
    public String toString() {
        return "EditCar{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", year_mfg='" + year_mfg + '\'' +
                ", img_url='" + img_url + '\'' +
                ", day_price=" + day_price +
                '}';
    }

    public static class CarBuilder {
        private EditCar newEditCar;

        public CarBuilder(long id) {
            newEditCar = new EditCar();
            newEditCar.id = id;
        }

        public EditCar.CarBuilder withBrand(String brand) {
            newEditCar.brand = brand;
            return this;
        }

        public EditCar.CarBuilder withModel(String model) {
            newEditCar.model = model;
            return this;
        }

        public EditCar.CarBuilder withType(String type) {
            newEditCar.type = type;
            return this;
        }

        public EditCar.CarBuilder withYear(String year) {
            newEditCar.year_mfg = year;
            return this;
        }

        public EditCar.CarBuilder withImg(String img) {
            newEditCar.img_url = img;
            return this;
        }

        public EditCar.CarBuilder withPrice(double price) {
            newEditCar.day_price = price;
            return this;
        }

        public EditCar build() {
            return newEditCar;
        }
    }
}
