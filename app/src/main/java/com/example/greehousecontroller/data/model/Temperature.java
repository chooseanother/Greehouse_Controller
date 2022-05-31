package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.greehousecontroller.utils.TemperatureJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.util.Calendar;
import java.util.TimeZone;

@Entity
@JsonAdapter(TemperatureJsonAdapter.class)
public class Temperature {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double temperature;
    private long time;

    @Ignore
    public Temperature() {
    }

    @Ignore
    public Temperature(double temperature, long time) {
        this.temperature = temperature;
        this.time = time;
    }

    public Temperature(int id, double temperature, long time) {
        this.id = id;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getLocalTime() {
        TimeZone localTimeZone = Calendar.getInstance().getTimeZone();
        int offset = localTimeZone.getRawOffset();
        if (localTimeZone.useDaylightTime()) {
            offset += localTimeZone.getDSTSavings();
        }
        return time + offset;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", time=" + time +
                '}';
    }
}
