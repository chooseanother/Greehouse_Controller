package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.greehousecontroller.utils.CO2JsonAdapter;
import com.google.gson.annotations.JsonAdapter;

@Entity
@JsonAdapter(CO2JsonAdapter.class)
public class CO2 {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double co2Measurement;
    private long time;


    @Ignore
    public CO2() {
    }

    public CO2(double co2Measurement, long time) {
        this.co2Measurement = co2Measurement;
        this.time = time;
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

    @Override
    public String toString() {
        return "CO2{" +
                "id=" + id +
                ", co2Measurement=" + co2Measurement +
                ", time=" + time +
                '}';
    }
}