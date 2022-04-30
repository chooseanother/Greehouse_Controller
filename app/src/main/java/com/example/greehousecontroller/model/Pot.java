package com.example.greehousecontroller.model;

public class Pot {
    private String name;
    private int currentHumidity;
    private int minimalHumidity;

    public Pot(String name, int currentHumidity, int minimalHumidity) {
        this.name = name;
        this.currentHumidity = currentHumidity;
        this.minimalHumidity = minimalHumidity;
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
