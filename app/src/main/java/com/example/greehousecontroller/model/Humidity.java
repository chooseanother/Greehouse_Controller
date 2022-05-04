package com.example.greehousecontroller.model;

public class Humidity {
    private double humidity;
    // TODO: Figure out how to convert the time in api to date
    private long time;
    private String greenhouseId;

    public Humidity() {
    }

    public Humidity(double humidity, long time, String greenhouseId) {
        this.humidity = humidity;
        this.time = time;
        this.greenhouseId = greenhouseId;
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
}
