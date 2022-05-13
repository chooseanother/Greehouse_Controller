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
import com.example.greehousecontroller.data.repository.TemperatureRepository;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<Pot>> pots;
    private final MutableLiveData<GreenHouse> greenHouseData;
    private final MutableLiveData<User> user;
    private TemperatureRepository temperatureRepository;
    private HumidityRepository humidityRepository;

    public HomeViewModel(Application application) {
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        humidityRepository = HumidityRepository.getInstance(application);
        //Getting data from repository in the future
        pots = new MutableLiveData<>();
        greenHouseData = new MutableLiveData<>();
        user = new MutableLiveData<>();
        ArrayList<Pot> newList = new ArrayList<>();
        pots.setValue(newList);

    }

    public MutableLiveData<ArrayList<Pot>> getAllPots() {
        return pots;
    }

    public void addPot(Pot pot){
        ArrayList<Pot> currentPots = pots.getValue();
        currentPots.add(pot);
        pots.setValue(currentPots);
    }

    public MutableLiveData<GreenHouse> getGreenHouseData() {
        return greenHouseData;
    }

    public MutableLiveData<User> getUser() {
        return user;
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
}