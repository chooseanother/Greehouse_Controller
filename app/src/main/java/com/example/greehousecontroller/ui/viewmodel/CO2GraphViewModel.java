package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.CO2Repository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

import java.util.List;

public class CO2GraphViewModel extends AndroidViewModel {
    private CO2Repository co2Repository;
    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;

    public CO2GraphViewModel(Application application) {
        super(application);
        co2Repository = CO2Repository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
    }
    public LiveData<List<CO2>> getCo2HistoryData() {
        return co2Repository.getCo2HistoryData();
    }
    public MutableLiveData<CO2> getLatestCO2(){
        return co2Repository.getLatest();
    }
    public LiveData<UserInfo> getUserInfo(){
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo(){
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    public void updateHistoryData(String greenhouseId)
    {
        co2Repository.updateLatestMeasurement(greenhouseId, null);
        co2Repository.updateHistoricalData(greenhouseId,null);
    }
}
