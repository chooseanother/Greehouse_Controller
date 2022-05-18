package com.example.greehousecontroller.data.model;

public class Threshold {
    private double upperThreshold;
    private double lowerThreshold;

    public Threshold(){

    }

    public Threshold(double upperThreshold, double lowerThreshold){
        this.upperThreshold = upperThreshold;
        this.lowerThreshold = lowerThreshold;
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
