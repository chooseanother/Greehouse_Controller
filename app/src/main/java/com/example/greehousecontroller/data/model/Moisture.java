package com.example.greehousecontroller.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import com.example.greehousecontroller.utils.MoistureJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

@Entity
@JsonAdapter(MoistureJsonAdapter.class)
public class Moisture {
    @PrimaryKey(autoGenerate = true)
    private double moisture;
    private long time;

    @Ignore
    public Moisture() {
    }

    public Moisture(double moisture, long time) {
        this.moisture = moisture;
        this.time = time;

    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Humidity{" +
                ", moisture=" + moisture +
                ", time=" + time +
                '}';
    }
}
