package com.example.greehousecontroller.data.model;

public class UserInfo {
    private String GreenhouseID;

    public UserInfo() {
    }

    public UserInfo(String GreenhouseID) {
        this.GreenhouseID = GreenhouseID;
    }

    public void setGreenhouseID(String greenhouseID) {
        GreenhouseID = greenhouseID;
    }

    public String getGreenhouseID() {
        return GreenhouseID;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "GreenhouseID='" + GreenhouseID + '\'' +
                '}';
    }
}
