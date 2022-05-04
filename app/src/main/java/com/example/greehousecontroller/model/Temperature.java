package com.example.greehousecontroller.model;

import java.util.Date;

public class Temperature {
    private double temperature;
    // TODO: Figure out how to convert the time in ap to date
    private long time;
    private String greenHouseId;

    public Temperature() {
    }

    public Temperature(double temperature, int time, String greenHouseId) {
        this.temperature = temperature;
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getGreenHouseId() {
        return greenHouseId;
    }

    public void setGreenHouseId(String greenHouseId) {
        this.greenHouseId = greenHouseId;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "temperature=" + temperature +
                ", time=" + time +
                ", greenHouseId='" + greenHouseId + '\'' +
                '}';
    }
}
