package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.model.GreenHouse;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.User;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    private TemperatureRepository temperatureRepository;
    private HumidityRepository humidityRepository;
    private PotRepository potRepository;
    private UserRepository userRepository;

    public HomeViewModel(Application application) {
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        humidityRepository = HumidityRepository.getInstance(application);
        potRepository = PotRepository.getInstance();
        userRepository = UserRepository.getInstance();
    }

    public MutableLiveData<ArrayList<Pot>> getAllPots(String greenhouseId) {
            return potRepository.getAllPots(greenhouseId);
    }

    public MutableLiveData<Temperature> getLatestTemperature() {
        return temperatureRepository.getLatest();
    }

    public MutableLiveData<Humidity> getLatestHumidity() {
        return humidityRepository.getLatest();
    }

    public void updateLatestMeasurements(String greenhouseId){
        temperatureRepository.updateLatestMeasurement(greenhouseId);
        humidityRepository.updateLatestMeasurement(greenhouseId);
    }

    public String getUser(){
        return userRepository.getCurrentUser().getValue();
    }
}