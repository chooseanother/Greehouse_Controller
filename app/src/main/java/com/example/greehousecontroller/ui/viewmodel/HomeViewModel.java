package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.CO2Repository;
import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final TemperatureRepository temperatureRepository;
    private final HumidityRepository humidityRepository;
    private final CO2Repository co2Repository;
    private final PotRepository potRepository;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private MutableLiveData<Boolean> refreshing;

    public HomeViewModel(Application application) {
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        humidityRepository = HumidityRepository.getInstance(application);
        potRepository = PotRepository.getInstance(application);
        co2Repository = CO2Repository.getInstance(application);
        userRepository = UserRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();

        refreshing = new MutableLiveData<>(false);
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

    public MutableLiveData<Boolean> getApiFinished() {
        return refreshing;
    }

    public void updateLatestMeasurements(String greenhouseId){
        refreshing.setValue(true);
        temperatureRepository.updateLatestMeasurement(greenhouseId, () -> {
            refreshing.postValue(false);
        });
        humidityRepository.updateLatestMeasurement(greenhouseId);
        potRepository.updateLatestMeasurement(greenhouseId);
        co2Repository.updateLatestMeasurement(greenhouseId);
    }

    public LiveData<UserInfo> getUserInfo(){
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo(){
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    public FirebaseUser getUser(){
        return userRepository.getCurrentUser().getValue();
    }

    public Bundle getPotBundle(Pot pot){
        Fragment fragment = new Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(pot.getId()));
        fragment.setArguments(bundle);
        return bundle;
    }

    public MutableLiveData<CO2> getLatestCO2() {
        return co2Repository.getLatest();
    }
}