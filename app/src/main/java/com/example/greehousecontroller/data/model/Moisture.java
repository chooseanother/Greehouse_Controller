package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.greehousecontroller.utils.MoistureJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.util.Calendar;
import java.util.TimeZone;

@Entity
@JsonAdapter(MoistureJsonAdapter.class)
public class Moisture {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double moisture;
    private long time;
    private int potId;

    @Ignore
    public Moisture() {
    }


    public Moisture(double moisture, long time, int potId) {
        this.moisture = moisture;
        this.time = time;
        this.potId = potId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPotId() {
        return potId;
    }

    public void setPotId(int potId) {
        this.potId = potId;
    }

    public long getLocalTime() {
        TimeZone localTimeZone = Calendar.getInstance().getTimeZone();
        int offset = localTimeZone.getRawOffset() + (localTimeZone.useDaylightTime() ? localTimeZone.getDSTSavings() : 0);
        return time + offset;
    }

    @Override
    public String toString() {
        return "Humidity{" +
                ", moisture=" + moisture +
                ", time=" + time +
                '}';
    }
}
