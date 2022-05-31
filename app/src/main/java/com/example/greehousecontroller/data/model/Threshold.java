package com.example.greehousecontroller.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Threshold {
    @PrimaryKey
    @NonNull
    private String type;
    private double upperThreshold;
    private double lowerThreshold;

    @Ignore
    public Threshold() {
    }

    @Ignore
    public Threshold(String type){
        this.type = type;
    }

    @Ignore
    public Threshold(double upperThreshold, double lowerThreshold) {
        this.upperThreshold = upperThreshold;
        this.lowerThreshold = lowerThreshold;
    }

    public Threshold(String type, double upperThreshold, double lowerThreshold) {
        this.type = type;
        this.upperThreshold = upperThreshold;
        this.lowerThreshold = lowerThreshold;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
