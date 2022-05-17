package com.example.greehousecontroller.data.model;

public class Pot {
    private int id;
    private String name;
    private double currentMoisture;
    private double lowerMoistureThreshold;

    public Pot(int id, String name, double currentMoisture, double lowerMoistureThreshold) {
        this.id = id;
        this.name = name;
        this.currentMoisture = currentMoisture;
        this.lowerMoistureThreshold = lowerMoistureThreshold;
    }

    public Pot(String name, double currentMoisture, double lowerMoistureThreshold) {
        this.name = name;
        this.currentMoisture = currentMoisture;
        this.lowerMoistureThreshold = lowerMoistureThreshold;
    }

    public Pot(int id, String name, double lowerMoistureThreshold){
        this.id = id;
        this.name = name;
        this.lowerMoistureThreshold = lowerMoistureThreshold;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCurrentMoisture() {
        return currentMoisture;
    }

    public double getLowerMoistureThreshold(){
        return lowerMoistureThreshold;
    }
}
