package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.database.AppDatabase;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.CO2Repository;
import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.repository.MoistureRepository;
import com.example.greehousecontroller.data.repository.PotRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditGreenhouseIdViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private UserInfoRepository userInfoRepository;
    private Application app;
    private AppDatabase database;
    private final ExecutorService executorService;
    private TemperatureRepository temperatureRepository;
    private HumidityRepository humidityRepository;
    private CO2Repository co2Repository;
    private PotRepository potRepository;
    private MoistureRepository moistureRepository;

    public EditGreenhouseIdViewModel(@NonNull Application application) {
        super(application);
        app = application;
        database = AppDatabase.getInstance(app);
        userRepository = UserRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        executorService = Executors.newFixedThreadPool(2);

        moistureRepository = MoistureRepository.getInstance(application);
        temperatureRepository = TemperatureRepository.getInstance(app);
        humidityRepository = HumidityRepository.getInstance(app);
        co2Repository = CO2Repository.getInstance(app);
        potRepository = PotRepository.getInstance(app);
    }

    public void initUserInfo(){
        String userId = userRepository.getCurrentUser().getValue().getUid();
        userInfoRepository.init(userId);
    }

    public LiveData<UserInfo> getCurrentUserInto(){
        return userInfoRepository.getUserInfo();
    }

    public String getGreenHouseId() {
        return userInfoRepository.getUserInfo().getValue().getGreenhouseID();
    }

    public void saveGreenHouseId(String id){
        userInfoRepository.saveGreenhouseID(id);
    }

    public void subscribeToGreenhouse(String greenhouseID){
        FirebaseMessaging.getInstance().subscribeToTopic(greenhouseID)
                .addOnCompleteListener(task -> {
                    String msg = "successfully subscribed to greenhouse "+ greenhouseID;
                    if (!task.isSuccessful()){
                        msg = "failed to subscribe to greenhouse " + greenhouseID;
                    }
                    Log.d("GreenhouseIDVM-sub",msg);
                    Toast.makeText(app.getApplicationContext(), R.string.edit_greenhouse_id_success, Toast.LENGTH_SHORT).show();
                });
    }

    public void unsubscribeFromGreenhouse(String greenhouseId){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(greenhouseId)
                .addOnCompleteListener(task -> {
                    String msg = "successfully unsubscribed from greenhouse "+ greenhouseId;
                    if (!task.isSuccessful()){
                        msg = "failed to unsubscribe from greenhouse " + greenhouseId;
                    }
                    Log.d("GreenhouseIDVM-unsub",msg);

                });
    }

    public void clearCachedDate(){
        executorService.execute(() -> database.clearAllTables());
    }
}
