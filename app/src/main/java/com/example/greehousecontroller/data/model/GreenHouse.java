package com.example.greehousecontroller.data.model;

public class GreenHouse {
    private int temperature;
    private int co2;
    private int humidity;
    private int luminosity;

    public GreenHouse(int temperature, int co2, int humidity, int luminosity) {
        this.temperature = temperature;
        this.co2 = co2;
        this.humidity = humidity;
        this.luminosity = luminosity;
    }


    public int getCo2() {
        return co2;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getLuminosity() {
        return luminosity;
    }


}
