package com.example.greehousecontroller.model;

public class CO2 {
    private double CO2;
    private long time;
    private String greenhouseId;
    // TODO: Implement JsonAdapterClass to handle converting to Date from seconds

    public CO2() {
    }

    public CO2(double CO2, long time, String greenhouseId) {
        this.CO2 = CO2;
        this.time = time;
        this.greenhouseId = greenhouseId;
    }

    public double getCO2() {
        return CO2;
    }

    public void setCO2(double CO2) {
        this.CO2 = CO2;
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
        return "CO2{" +
                "CO2=" + CO2 +
                ", time=" + time +
                ", greenhouseId='" + greenhouseId + '\'' +
                '}';
    }
}
