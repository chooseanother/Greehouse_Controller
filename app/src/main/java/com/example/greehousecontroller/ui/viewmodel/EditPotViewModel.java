package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

public class EditPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private Application application;


    public EditPotViewModel(Application application) {
        super(application);
        this.application = application;
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

    public boolean updateCurrentPot(String greenhouseId, int id, String name, String minimumThreshold) {
        if (!checkForNameInput(name)) {
            Toast.makeText(application, R.string.add_pot_missing_name_exception, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkForThresholdType(minimumThreshold)) {
            Toast.makeText(application, R.string.settings_not_a_number_exception, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkForThresholdNumberSize(minimumThreshold)) {
            Toast.makeText(application, R.string.settings_out_of_bounds_humidity_exception, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            potRepository.updateCurrentPot(greenhouseId, id, name, Double.parseDouble(minimumThreshold));
            return true;
        }
    }

    private boolean checkForThresholdNumberSize(String minimumThreshold) {
        if (!minimumThreshold.isEmpty()) {
            if (Double.parseDouble(minimumThreshold) <= 100.0 && Double.parseDouble(minimumThreshold) >= 0.0) {
                return true;
            } else {
                return false;
            }
        } else {

            return false;
        }
    }

    private boolean checkForThresholdType(String minimumThreshold) {
        if (minimumThreshold.equals(".")) {
            return false;
        } else {
            return true;
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