package com.example.greehousecontroller.data.model;

import com.example.greehousecontroller.utils.TemperatureJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.util.Date;

@JsonAdapter(TemperatureJsonAdapter.class)
public class Temperature {
    private double temperature;
    private Date time;

    public Temperature() {
    }

    public Temperature(double temperature, Date time) {
        this.temperature = temperature;
        this.time = time;
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
