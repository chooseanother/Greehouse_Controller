package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

import java.util.ArrayList;

public class HumidityGraphViewModel extends AndroidViewModel {
    private HumidityRepository humidityRepository;
    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;
    public HumidityGraphViewModel(Application application) {
        super(application);
        humidityRepository =  HumidityRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
    }
    public MutableLiveData<Humidity> getLatestHumidity(){
        return humidityRepository.getLatest();
    }

    public LiveData<ArrayList<Humidity>> getHumidityHistoryData() {
        return humidityRepository.getHistoricalData();
    }
    public LiveData<UserInfo> getUserInfo(){
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo(){
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    public void updateHistoryData(String greenhouseId)
    {
        humidityRepository.updateHistoricalData(greenhouseId);
    }
}
