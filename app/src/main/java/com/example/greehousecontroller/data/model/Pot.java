package com.example.greehousecontroller.data.model;

public class Pot {
    private int id;
    private String name;
    private float currentHumidity;
    private float minimalHumidity;

    public Pot(int id, String name, float currentHumidity, float minimalHumidity) {
        this.id = id;
        this.name = name;
        this.currentHumidity = currentHumidity;
        this.minimalHumidity = minimalHumidity;
    }

    public Pot(String name, float currentHumidity, float minimalHumidity) {
        this.name = name;
        this.currentHumidity = currentHumidity;
        this.minimalHumidity = minimalHumidity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getCurrentHumidity() {
        return currentHumidity;
    }

    public float getMinimalHumidity(){
        return minimalHumidity;
    }
}
