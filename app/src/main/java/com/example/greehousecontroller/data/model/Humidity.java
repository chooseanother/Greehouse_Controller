package com.example.greehousecontroller.data.model;

public class Humidity {
    private double humidity;
    // TODO: Implement JsonAdapterClass to handle converting to Date from seconds
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

    @Override
    public String toString() {
        return "Humidity{" +
                "humidity=" + humidity +
                ", time=" + time +
                ", greenhouseId='" + greenhouseId + '\'' +
                '}';
    }
}
