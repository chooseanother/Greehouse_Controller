package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.TemperatureRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

import java.util.List;

public class TemperatureGraphViewModel extends AndroidViewModel {
    private final TemperatureRepository temperatureRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    public TemperatureGraphViewModel(Application application) {
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
    }

    public LiveData<List<Temperature>> getTemperatureHistoryData() {
        return temperatureRepository.getTemperatureHistoryData();
    }

    public MutableLiveData<Temperature> getLatestTemperature() {
        return temperatureRepository.getLatest();
    }

    public LiveData<UserInfo> getUserInfo() {
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo() {
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    public void updateHistoryData(String greenhouseId) {
        temperatureRepository.updateHistoricalMeasurement(greenhouseId, null);
        temperatureRepository.updateLatestMeasurement(greenhouseId, null);
    }

    public void loadCachedData() {
        temperatureRepository.loadLatestCachedData();
        temperatureRepository.loadHistoricalCachedData();
    }
}
