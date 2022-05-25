package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Pot {
    @PrimaryKey
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

    @Ignore
    public Pot(String name, double currentMoisture, double lowerMoistureThreshold) {
        this.name = name;
        this.currentMoisture = currentMoisture;
        this.lowerMoistureThreshold = lowerMoistureThreshold;
    }

    @Ignore
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
