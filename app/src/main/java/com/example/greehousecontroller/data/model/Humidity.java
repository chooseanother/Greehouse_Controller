package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Humidity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double humidity;
    private long time;
    private String greenhouseId;

    @Ignore
    public Humidity() {
    }

    public Humidity(double humidity, long time, String greenhouseId) {
        this.humidity = humidity;
        this.time = time;
        this.greenhouseId = greenhouseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
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
        return "Humidity{" +
                "humidity=" + humidity +
                ", time=" + time +
                ", greenhouseId='" + greenhouseId + '\'' +
                '}';
    }
}
