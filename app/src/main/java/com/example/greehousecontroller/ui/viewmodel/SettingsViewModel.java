package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.greehousecontroller.data.model.Threshold;
import com.example.greehousecontroller.data.repository.CO2Repository;
import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;

public class SettingsViewModel extends AndroidViewModel {

    private TemperatureRepository temperatureRepository;
    private HumidityRepository humidityRepository;
    private CO2Repository co2Repository;

    public SettingsViewModel(Application application){
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        humidityRepository = HumidityRepository.getInstance(application);
        co2Repository = CO2Repository.getInstance(application);
    }

    public void initializeData(String greenHouseId){
        temperatureRepository.updateThreshold(greenHouseId);
        humidityRepository.updateThreshold(greenHouseId);
        co2Repository.updateThreshold(greenHouseId);
    }

    public MutableLiveData<Threshold> getTemperatureThreshold(){
        return temperatureRepository.getThreshold();
    }
    public MutableLiveData<Threshold> getHumidityThreshold(){
        return humidityRepository.getThreshold();
    }
    public MutableLiveData<Threshold> getCo2Threshold(){
        return co2Repository.getThreshold();
    }

    public boolean setTemperatureThreshold(String greenhouseId, String upperThreshold, String lowerThreshold){
        try{
            double upperThresholdDouble = Double.parseDouble(upperThreshold);
            double lowerThresholdDouble = Double.parseDouble(lowerThreshold);
            Threshold newThreshold = new Threshold(upperThresholdDouble, lowerThresholdDouble);
            temperatureRepository.setThreshold(greenhouseId, newThreshold);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    public boolean setCo2Threshold(String greenhouseId, String upperThreshold, String lowerThreshold){
        try{
            double upperThresholdDouble = Double.parseDouble(upperThreshold);
            double lowerThresholdDouble = Double.parseDouble(lowerThreshold);
            Threshold newThreshold = new Threshold(upperThresholdDouble, lowerThresholdDouble);
            co2Repository.setThreshold(greenhouseId, newThreshold);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    public boolean setHumidityThreshold(String greenhouseId, String upperThreshold, String lowerThreshold){
        try{
            double upperThresholdDouble = Double.parseDouble(upperThreshold);
            double lowerThresholdDouble = Double.parseDouble(lowerThreshold);
            Threshold newThreshold = new Threshold(upperThresholdDouble, lowerThresholdDouble);
            humidityRepository.setThreshold(greenhouseId, newThreshold);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}