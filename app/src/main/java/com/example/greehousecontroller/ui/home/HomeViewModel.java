package com.example.greehousecontroller.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.greehousecontroller.Repository.Measurements.Temperature.TemperatureRepository;
import com.example.greehousecontroller.model.GreenHouse;
import com.example.greehousecontroller.model.Pot;
import com.example.greehousecontroller.model.Temperature;
import com.example.greehousecontroller.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<Pot>> pots;
    private final MutableLiveData<GreenHouse> greenHouseData;
    private final MutableLiveData<User> user;
    private TemperatureRepository temperatureRepository;

    public HomeViewModel(Application application) {
        super(application);
        //Getting data from repository in the future
        pots = new MutableLiveData<>();
        greenHouseData = new MutableLiveData<>();
        user = new MutableLiveData<>();
        ArrayList<Pot> newList = new ArrayList<>();
        pots.setValue(newList);

        temperatureRepository = TemperatureRepository.getInstance(application);
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

    public MutableLiveData<Temperature> getTemperature(){
        return temperatureRepository.getLatest();
    }

    public void updateMeasurements(){
        // TODO: handle greenhouseid
        String greenhouseid = "test";
        temperatureRepository.updateLatestMeasurement(greenhouseid);
    }
}