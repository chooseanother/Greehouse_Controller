package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;
import com.example.greehousecontroller.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class AddPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final Application application;
    private final List<String> listOfSensors;

    public AddPotViewModel(Application application) {
        super(application);
        this.application = application;
        potRepository = PotRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
        listOfSensors = new ArrayList<>();
    }

    public boolean validInput(String greenhouseId, String moistureSensorId, String name, String minimumHumidity) {
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
        }
        if (!checkForSensorSelection(moistureSensorId)) {
            Toast.makeText(application, R.string.add_pot_missing_sensor_exception, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkForSensorSelection(String moistureSensorId) {
        return moistureSensorId != null;
    }

    public void addPot(String greenhouseId, String moistureSensorId, String name, String minimumHumidity) {
        potRepository.addPot(greenhouseId, Integer.parseInt(moistureSensorId) - 1, name, Double.parseDouble(minimumHumidity));
    }

    private boolean checkForThresholdNumberSize(String minimumThreshold) {
        if (!minimumThreshold.isEmpty()) {
            return Double.parseDouble(minimumThreshold) <= 100.0 && Double.parseDouble(minimumThreshold) >= 0.0;
        } else {

            return false;
        }
    }

    private boolean checkForThresholdType(String minimumThreshold) {
        return !minimumThreshold.equals(".");
    }

    public LiveData<UserInfo> getUserInfo() {
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo() {
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    private boolean checkForNameInput(String name) {
        return name.length() <= 100 && !name.equals("") && name != null;
    }

    public List<String> getAvailableSensors() {
        List<Pot> temp = potRepository.getPots().getValue();

        if (temp == null || temp.isEmpty()) {
            for (int i = 1; i <= Config.MAX_NUMBER_OF_POTS; i++) {
                listOfSensors.add("" + i);
            }
        } else {
            ArrayList<String> temp2 = new ArrayList<>();

            for (int i = 0; i < temp.size(); i++) {
                temp2.add(String.valueOf(temp.get(i).getMoistureSensorId()));
            }

            for (int i = 0; i < Config.MAX_NUMBER_OF_POTS; i++) {
                if (!temp2.contains(String.valueOf(i))) {
                    listOfSensors.add(String.valueOf(i + 1));
                }
            }

        }
        return listOfSensors;
    }
}