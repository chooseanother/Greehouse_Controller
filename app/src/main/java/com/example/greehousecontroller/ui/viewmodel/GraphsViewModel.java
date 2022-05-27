package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.CO2Repository;
import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.repository.MoistureRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;
import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphsViewModel extends AndroidViewModel {


    private TemperatureRepository temperatureRepository;
    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;
    private HumidityRepository humidityRepository;
    private CO2Repository co2Repository;

    public GraphsViewModel(Application application){
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
        humidityRepository = HumidityRepository.getInstance(application);
        co2Repository = CO2Repository.getInstance(application);
    }
    public LiveData<List<Temperature>> getTemperatureHistoryData() {
        return temperatureRepository.getTemperatureHistoryData();
    }
    public void updateHistoryData(String greenHouseId)
    {
        humidityRepository.updateHistoricalData(greenHouseId);
        co2Repository.updateHistoricalData(greenHouseId);
        temperatureRepository.updateHistoricalMeasurement(greenHouseId);
    }
    public LiveData<UserInfo> getUserInfo(){
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo(){
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }


}