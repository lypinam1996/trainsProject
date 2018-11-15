package com.tsystems.trainsProject.models;

public class Car {

    private final String model;
    private final int price;
    private final int housePower;

    public Car(String model, int price, int housePower) {
        this.model = model;
        this.price = price;
        this.housePower = housePower;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public int getHousePower() {
        return housePower;
    }
}
