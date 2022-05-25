package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

public class AddPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;
    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;
    public AddPotViewModel(Application application) {
        super(application);
        potRepository = PotRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
    }

    public String validInput(String greenhouseId, String name, double minimumHumidity){
            if(checkForNameInput(name) && checkForThresholdInput(minimumHumidity)){
                addPot(greenhouseId, name, minimumHumidity);
                return "";
            }
            else
            {
                return "Check the input";
            }
    }

    public void addPot(String greenhouseId, String name, double minimumHumidity){
        potRepository.addPot(greenhouseId, name, minimumHumidity);
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