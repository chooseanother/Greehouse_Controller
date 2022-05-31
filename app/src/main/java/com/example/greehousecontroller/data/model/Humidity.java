package com.example.greehousecontroller.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.greehousecontroller.utils.HumidityJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.util.Calendar;
import java.util.TimeZone;

@Entity
@JsonAdapter(HumidityJsonAdapter.class)
public class Humidity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double humidity;
    private long time;


    @Ignore
    public Humidity() {
    }

    public Humidity(double humidity, long time) {
        this.humidity = humidity;
        this.time = time;

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
        return "Humidity{" +
                "id=" + id +
                ", humidity=" + humidity +
                ", time=" + time +
                '}';
    }
}
