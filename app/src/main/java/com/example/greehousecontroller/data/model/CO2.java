package com.example.greehousecontroller.data.model;

public class CO2 {
    private double dioxideCarbon;
    private long time;
    private String greenhouseId;
    // TODO: Implement JsonAdapterClass to handle converting to Date from seconds

    public CO2() {
    }

    public CO2(double dioxideCarbon, long time, String greenhouseId) {
        this.dioxideCarbon = dioxideCarbon;
        this.time = time;
        this.greenhouseId = greenhouseId;
    }

    public double getCO2() {
        return dioxideCarbon;
    }

    public void setCO2(double dioxideCarbon) {
        this.dioxideCarbon = dioxideCarbon;
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
                "CO2=" + dioxideCarbon +
                ", time=" + time +
                ", greenhouseId='" + greenhouseId + '\'' +
                '}';
    }
}