package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class CO2 {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double co2Measurement;
    private long time;
    private String greenhouseId;
    // TODO: Implement JsonAdapterClass to handle converting to Date from seconds

    @Ignore
    public CO2() {
    }

    public CO2(double dioxideCarbon, long time, String greenhouseId) {
        this.co2Measurement = dioxideCarbon;
        this.time = time;
        this.greenhouseId = greenhouseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCo2Measurement() {
        return co2Measurement;
    }

    public void setCo2Measurement(double co2Measurement) {
        this.co2Measurement = co2Measurement;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getGreenhouseId() {
        return greenhouseId;
    }

    public void setGreenhouseId(String greenhouseId) {
        this.greenhouseId = greenhouseId;
    }

    @Override
    public String toString() {
        return "CO2{" +
                "CO2=" + co2Measurement +
                ", time=" + time +
                ", greenhouseId='" + greenhouseId + '\'' +
                '}';
    }
}