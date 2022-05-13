package com.example.greehousecontroller.data.model;

public class Pot {
    private int id;
    private String name;
    private int currentHumidity;
    private int minimalHumidity;

    public Pot(int id, String name, int currentHumidity, int minimalHumidity) {
        this.id = id;
        this.name = name;
        this.currentHumidity = currentHumidity;
        this.minimalHumidity = minimalHumidity;
    }

    public Pot(String name, int currentHumidity, int minimalHumidity) {
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

    public int getCurrentHumidity() {
        return currentHumidity;
    }

    public int getMinimalHumidity(){
        return minimalHumidity;
    }
}
