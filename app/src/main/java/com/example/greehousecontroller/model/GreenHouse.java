package com.example.greehousecontroller.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.Repository.Measurements.Temperature.TemperatureRepository;

public class GreenHouse {
    private TemperatureRepository temperature;
    private int co2;
    private int humidity;
    private int luminosity;
    private String greenhouseId = "test";

    public GreenHouse(Application application, int co2, int humidity, int luminosity) {
        temperature = TemperatureRepository.getInstance(application);
        this.co2 = co2;
        this.humidity = humidity;
        this.luminosity = luminosity;
    }

    public MutableLiveData<Temperature> getTemperature() {
        return temperature.getLatest();
    }

    public int getCo2() {
        return co2;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getLuminosity() {
        return luminosity;
    }

    public void updateMeasurements(){
        temperature.updateLatestMeasurement(greenhouseId);
    }
}
