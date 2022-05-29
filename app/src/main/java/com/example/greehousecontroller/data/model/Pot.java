package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Pot {
    @PrimaryKey
    private int id;
    private String name;
    private int moistureSensorId;
    private double currentMoisture;
    private double lowerMoistureThreshold;

    public Pot(int id, String name, int moistureSensorId, double currentMoisture, double lowerMoistureThreshold) {
        this.id = id;
        this.name = name;
        this.moistureSensorId = moistureSensorId;
        this.currentMoisture = currentMoisture;
        this.lowerMoistureThreshold = lowerMoistureThreshold;
    }

    @Ignore
    public Pot(String name, int moistureSensorId, double currentMoisture, double lowerMoistureThreshold) {
        this.name = name;
        this.moistureSensorId = moistureSensorId;
        this.currentMoisture = currentMoisture;
        this.lowerMoistureThreshold = lowerMoistureThreshold;
    }

    @Ignore
    public Pot(int id, int moistureSensorId, String name, double lowerMoistureThreshold){
        this.id = id;
        this.moistureSensorId = moistureSensorId;
        this.name = name;
        this.lowerMoistureThreshold = lowerMoistureThreshold;
    }

    @Ignore
    public Pot(int id, String name, double lowerMoistureThreshold){
        this.id = id;
        this.name = name;
        this.lowerMoistureThreshold = lowerMoistureThreshold;
    }

    @Ignore
    public Pot(String name, double currentMoisture, double lowerMoistureThreshold) {
        this.name = name;
        this.currentMoisture = currentMoisture;
        this.lowerMoistureThreshold = lowerMoistureThreshold;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMoistureSensorId(){ return moistureSensorId; }

    public double getCurrentMoisture() {
        return currentMoisture;
    }

    public double getLowerMoistureThreshold(){
        return lowerMoistureThreshold;
    }
}
