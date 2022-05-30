package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.model.Moisture;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.MoistureRepository;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

import java.util.List;

public class MoistureViewModel extends AndroidViewModel {

    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;
    private Application application;
    private MoistureRepository moistureRepository;
    private final PotRepository potRepository;
    private MutableLiveData<Boolean> refreshing;
    public MoistureViewModel(Application application) {
        super(application);
        this.application = application;
        potRepository = PotRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
        moistureRepository = MoistureRepository.getInstance(application);

        refreshing = new MutableLiveData<>(false);
    }

    public LiveData<UserInfo> getUserInfo() {
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo() {
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }
    public void updateHistoryData(String greenHouseId,int potId)
    {
        moistureRepository.loadCachedData(potId);
        moistureRepository.updateHistoricalData(greenHouseId,potId, () -> {
            refreshing.postValue(false);
        });
        moistureRepository.updateLatestData(greenHouseId,potId, () -> {
            refreshing.postValue(false);
        });
    }

    public MutableLiveData<Boolean> getRefreshing() {
        return refreshing;
    }

    public LiveData<List<Moisture>> getMoistureHistoryData()
    {
        return moistureRepository.getHistoricalData();
    }
    public LiveData<Moisture> getLatestHumidity()
    {
        return moistureRepository.getLatest();
    }
    public LiveData<Pot> getPot()
    {
        return potRepository.getCurrentPot();
    }
    public LiveData<List<Pot>> getPots()
    {
        return potRepository.getPots();
    }
}
