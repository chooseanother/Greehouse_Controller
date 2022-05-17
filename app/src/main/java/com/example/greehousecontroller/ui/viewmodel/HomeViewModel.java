package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;
import com.example.greehousecontroller.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private TemperatureRepository temperatureRepository;
    private HumidityRepository humidityRepository;
    private PotRepository potRepository;
    private UserRepository userRepository;

    public HomeViewModel(Application application) {
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        humidityRepository = HumidityRepository.getInstance(application);
        potRepository = PotRepository.getInstance(application);
        userRepository = UserRepository.getInstance(application);
    }

    public MutableLiveData<List<Pot>> getLatestPots() {
        return potRepository.getPots();
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
        potRepository.updateLatestMeasurement(greenhouseId);
    }

    public FirebaseUser getUser(){
        return userRepository.getCurrentUser().getValue();
    }
}