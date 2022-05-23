package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.greehousecontroller.utils.TemperatureJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.util.Date;

@Entity
@JsonAdapter(TemperatureJsonAdapter.class)
public class Temperature {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double temperature;
    private Date time;

    @Ignore
    public Temperature() {
    }

    public Temperature(double temperature, Date time) {
        this.temperature = temperature;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "temperature=" + temperature +
                ", time=" + time +
                 '\'' +
                '}';
    }
}
