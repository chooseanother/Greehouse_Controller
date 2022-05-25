package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

public class EditPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;


    public EditPotViewModel(Application application) {
        super(application);
        potRepository = PotRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
    }

    public void init(String greenhouseId, int id) {
        potRepository.init(greenhouseId, id);
    }

    public MutableLiveData<Pot> getCurrentPot() {
        return potRepository.getCurrentPot();
    }

    public String updateCurrentPot(String greenhouseId, int id, String name, double minimumThreshold) {
        if(checkForThresholdInput(minimumThreshold) && checkForNameInput(name)){
            potRepository.updateCurrentPot(greenhouseId, id, name, minimumThreshold);
            return "";
        }
        else
        {
            return "Check the input";
        }
    }

    private boolean checkForThresholdInput(double minimumThreshold){
        if(minimumThreshold <= 100.0 && minimumThreshold >= 0.0){
            return true;
        }
        else{
            return false;
        }
    }
    public LiveData<UserInfo> getUserInfo(){
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo(){
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    private boolean checkForNameInput(String name){
        if(name.length() > 100 || name.equals("") || name == null){
            return false;
        }
        else{
            return true;
        }
    }
}