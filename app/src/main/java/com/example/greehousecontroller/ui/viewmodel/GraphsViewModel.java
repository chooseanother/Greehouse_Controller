package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.CO2Repository;
import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

import java.util.List;

public class GraphsViewModel extends AndroidViewModel {


    private final TemperatureRepository temperatureRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final HumidityRepository humidityRepository;
    private final CO2Repository co2Repository;
    private final MutableLiveData<Boolean> refreshing;

    public GraphsViewModel(Application application) {
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
        humidityRepository = HumidityRepository.getInstance(application);
        co2Repository = CO2Repository.getInstance(application);

        refreshing = new MutableLiveData<>(false);
    }

    public LiveData<List<Temperature>> getTemperatureHistoryData() {
        return temperatureRepository.getTemperatureHistoryData();
    }

    public void updateHistoryData(String greenHouseId) {
        humidityRepository.updateHistoricalData(greenHouseId, () -> {
            refreshing.setValue(false);
        });
        co2Repository.updateHistoricalData(greenHouseId, () -> {
            refreshing.setValue(false);
        });
        temperatureRepository.updateHistoricalMeasurement(greenHouseId, () -> {
            refreshing.postValue(false);
        });

    }

    public MutableLiveData<Boolean> getRefreshing() {
        return refreshing;
    }

    public LiveData<UserInfo> getUserInfo() {
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo() {
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    public void loadCachedData() {
        temperatureRepository.loadLatestCachedData();
        temperatureRepository.loadHistoricalCachedData();
    }

}