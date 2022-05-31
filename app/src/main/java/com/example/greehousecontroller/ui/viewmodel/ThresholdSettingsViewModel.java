package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Threshold;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.CO2Repository;
import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;
import com.example.greehousecontroller.utils.Config;

public class ThresholdSettingsViewModel extends AndroidViewModel {
    private final TemperatureRepository temperatureRepository;
    private final HumidityRepository humidityRepository;
    private final CO2Repository co2Repository;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    Application application;

    public ThresholdSettingsViewModel(Application application) {
        super(application);
        this.application = application;
        temperatureRepository = TemperatureRepository.getInstance(application);
        humidityRepository = HumidityRepository.getInstance(application);
        co2Repository = CO2Repository.getInstance(application);
        userRepository = UserRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
    }

    public void initializeData(String greenHouseId) {
        temperatureRepository.updateThreshold(greenHouseId);
        humidityRepository.updateThreshold(greenHouseId);
        co2Repository.updateThreshold(greenHouseId);
    }

    public void loadCachedData() {
        humidityRepository.loadThresholdCachedData();
        temperatureRepository.loadThresholdCachedData();
        co2Repository.loadThresholdCachedData();
    }

    public LiveData<UserInfo> getUserInfo() {
        return userInfoRepository.getUserInfo();
    }

    public void initUserInfo() {
        userInfoRepository.init(userRepository.getCurrentUser().getValue().getUid());
    }

    public MutableLiveData<Threshold> getTemperatureThreshold() {
        return temperatureRepository.getThreshold();
    }

    public MutableLiveData<Threshold> getHumidityThreshold() {
        return humidityRepository.getThreshold();
    }

    public MutableLiveData<Threshold> getCo2Threshold() {
        return co2Repository.getThreshold();
    }

    public void setTemperatureThreshold(String greenhouseId, String upperThreshold, String lowerThreshold) {
        try {
            double upperThresholdDouble = Double.parseDouble(upperThreshold);
            double lowerThresholdDouble = Double.parseDouble(lowerThreshold);

            //temperature must be between -20 and 60
            if (upperThresholdDouble > Config.MAX_UPPER_THRESHOLD_TEMPERATURE || lowerThresholdDouble < Config.MIN_LOWER_THRESHOLD_TEMPERATURE) {
                Toast.makeText(application, R.string.settings_out_of_bounds_temperature_exception, Toast.LENGTH_SHORT).show();
            } else {
                //upper cannot be lower than lower
                if (checkLowerThresholdNotHigherThanUpper(lowerThresholdDouble, upperThresholdDouble)) {
                    Threshold newThreshold = new Threshold(upperThresholdDouble, lowerThresholdDouble);
                    temperatureRepository.setThreshold(greenhouseId, newThreshold);
                    Toast.makeText(application, R.string.settings_changes_saved, Toast.LENGTH_SHORT).show();
                }
            }

        } catch (NumberFormatException e) {
            Toast.makeText(application, R.string.settings_not_a_number_exception, Toast.LENGTH_SHORT).show();
        }
    }

    public void setCo2Threshold(String greenhouseId, String upperThreshold, String lowerThreshold) {
        try {
            double upperThresholdDouble = Double.parseDouble(upperThreshold);
            double lowerThresholdDouble = Double.parseDouble(lowerThreshold);
            //co2 must be between 0 and 5000
            if (upperThresholdDouble > Config.MAX_UPPER_THRESHOLD_CO2 || lowerThresholdDouble < Config.MIN_LOWER_THRESHOLD_CO2) {
                Toast.makeText(application, R.string.settings_out_of_bounds_co2_exception, Toast.LENGTH_SHORT).show();
            } else {
                //upper cannot be lower than lower
                if (checkLowerThresholdNotHigherThanUpper(lowerThresholdDouble, upperThresholdDouble)) {
                    Threshold newThreshold = new Threshold(upperThresholdDouble, lowerThresholdDouble);
                    co2Repository.setThreshold(greenhouseId, newThreshold);
                    Toast.makeText(application, R.string.settings_changes_saved, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(application, R.string.settings_not_a_number_exception, Toast.LENGTH_SHORT).show();
        }
    }

    public void setHumidityThreshold(String greenhouseId, String upperThreshold, String lowerThreshold) {
        try {
            double upperThresholdDouble = Double.parseDouble(upperThreshold);
            double lowerThresholdDouble = Double.parseDouble(lowerThreshold);
            //humidity must be between 0 and 100
            if (upperThresholdDouble > Config.MAX_UPPER_THRESHOLD_HUMIDITY || lowerThresholdDouble < Config.MIN_LOWER_THRESHOLD_HUMIDITY) {
                Toast.makeText(application, R.string.settings_out_of_bounds_co2_exception, Toast.LENGTH_SHORT).show();
            } else {
                //upper cannot be lower than lower
                if (checkLowerThresholdNotHigherThanUpper(lowerThresholdDouble, upperThresholdDouble)) {
                    Threshold newThreshold = new Threshold(upperThresholdDouble, lowerThresholdDouble);
                    humidityRepository.setThreshold(greenhouseId, newThreshold);
                    Toast.makeText(application, R.string.settings_changes_saved, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(application, R.string.settings_not_a_number_exception, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkLowerThresholdNotHigherThanUpper(double lowerThreshold, double upperThreshold) {
        if (upperThreshold < lowerThreshold) {
            Toast.makeText(application, R.string.settings_lower_higher_than_upper_threshold_exception, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}