package com.example.greehousecontroller.data.model;

public class Sensor {
    String sensor;
    boolean status;

    public Sensor() {

    }

    public Sensor(String sensor, boolean status) {
        this.sensor = sensor;
        this.status = status;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
