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

import java.util.ArrayList;
import java.util.List;

public class AddPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;
    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;
    private Application application;
    private List<String> listOfSensors;

    public AddPotViewModel(Application application) {
        super(application);
        this.application = application;
        potRepository = PotRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
        listOfSensors = new ArrayList<>();
    }

    public boolean validInput(String greenhouseId, String sensorId, String name, String minimumHumidity) {
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
        if(!checkForSensorSelection(sensorId)){
            Toast.makeText(application, R.string.add_pot_missing_sensor_exception, Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            addPot(greenhouseId, Integer.parseInt(sensorId)-1, name, Double.parseDouble(minimumHumidity));
            return true;
        }
    }

    private boolean checkForSensorSelection(String sensorId) {
        if(sensorId == null){
            return false;
        }
        else{
            return true;
        }
    }

    public void addPot(String greenhouseId, int sensorId, String name, double minimumHumidity) {
        potRepository.addPot(greenhouseId, sensorId, name, minimumHumidity);
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

    public List<String> getAvailableSensors() {
        List<Pot> temp = potRepository.getPots().getValue();

        if(temp == null || temp.isEmpty()){
            listOfSensors.add("1");
            listOfSensors.add("2");
            listOfSensors.add("3");
            listOfSensors.add("4");
            listOfSensors.add("5");
            listOfSensors.add("6");
        }
        else
        {
            ArrayList<String> temp2 = new ArrayList<>();

            for(int i= 0; i< temp.size(); i++){
                temp2.add(String.valueOf(temp.get(i).getSensorId()));
            }

            for(int i= 0; i< 6; i++){
                if(!temp2.contains(String.valueOf(i))){
                    listOfSensors.add(String.valueOf(i+1));
                }
            }

        }
        return listOfSensors;
    }
}