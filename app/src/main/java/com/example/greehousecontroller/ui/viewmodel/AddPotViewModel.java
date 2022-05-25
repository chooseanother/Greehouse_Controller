package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;

public class AddPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;
    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;
    private Application application;

    public AddPotViewModel(Application application) {
        super(application);
        this.application = application;
        potRepository = PotRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
    }

    public boolean validInput(String greenhouseId, String name, String minimumHumidity) {
        if (!checkForNameInput(name)) {
            Toast.makeText(application, R.string.add_pot_missing_name_exception, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkForThresholdType(minimumHumidity)) {
            Toast.makeText(application, R.string.settings_not_a_number_exception, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkForThresholdNumberSize(minimumHumidity)) {
            Toast.makeText(application, R.string.settings_out_of_bounds_humidity_exception, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            addPot(greenhouseId, name, Double.parseDouble(minimumHumidity));
            return true;
        }
    }

    public void addPot(String greenhouseId, String name, double minimumHumidity) {
        potRepository.addPot(greenhouseId, name, minimumHumidity);
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

    public LiveData<UserInfo> getUserInfo() {
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo() {
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    private boolean checkForNameInput(String name) {
        if (name.length() > 100 || name.equals("") || name == null) {
            return false;
        } else {
            return true;
        }
    }
}