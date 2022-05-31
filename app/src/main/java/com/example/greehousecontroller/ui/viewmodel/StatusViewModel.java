package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.greehousecontroller.data.model.Sensor;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.SensorRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

import java.util.List;

public class StatusViewModel extends AndroidViewModel {
    private final SensorRepository sensorRepository;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;


    public StatusViewModel(Application application) {
        super(application);
        sensorRepository = SensorRepository.getInstance(application);
        userRepository = UserRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
    }

    public MutableLiveData<List<Sensor>> getSensorsStatus(){
        return sensorRepository.getSensors();
    }

    public void initUserInfo(){
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    public LiveData<UserInfo> getUserInfo(){
        return userInfoRepository.getUserInfo();
    }

    public void updateSensorStatus(String greenhouseId){
        sensorRepository.updateSensors(greenhouseId);
    }
}