package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Threshold {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double upperThreshold;
    private double lowerThreshold;

    @Ignore
    public Threshold(){
    }

    public Threshold(double upperThreshold, double lowerThreshold){
        this.upperThreshold = upperThreshold;
        this.lowerThreshold = lowerThreshold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getUpperThreshold() {
        return upperThreshold;
    }

    public void setUpperThreshold(double upperThreshold) {
        this.upperThreshold = upperThreshold;
    }

    public double getLowerThreshold() {
        return lowerThreshold;
    }

    public void setLowerThreshold(double lowerThreshold) {
        this.lowerThreshold = lowerThreshold;
    }

    @Override
    public String toString() {
        return "Threshold{" +
                "upperThreshold=" + upperThreshold +
                ", lowerThreshold=" + lowerThreshold +
                '\'' +
                '}';
    }
}
