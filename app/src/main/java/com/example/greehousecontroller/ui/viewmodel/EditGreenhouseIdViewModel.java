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
import com.example.greehousecontroller.utils.GreenhouseFirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditGreenhouseIdViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final Application app;
    private final AppDatabase database;
    private final ExecutorService executorService;
    private final TemperatureRepository temperatureRepository;
    private final HumidityRepository humidityRepository;
    private final CO2Repository co2Repository;
    private final PotRepository potRepository;
    private final MoistureRepository moistureRepository;
    private final WeakReference<GreenhouseFirebaseMessagingService> messagingService;

    public EditGreenhouseIdViewModel(@NonNull Application application) {
        super(application);
        app = application;
        database = AppDatabase.getInstance(app);
        userRepository = UserRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        executorService = Executors.newFixedThreadPool(2);
        messagingService = new WeakReference<>(new GreenhouseFirebaseMessagingService());
        moistureRepository = MoistureRepository.getInstance(application);
        temperatureRepository = TemperatureRepository.getInstance(app);
        humidityRepository = HumidityRepository.getInstance(app);
        co2Repository = CO2Repository.getInstance(app);
        potRepository = PotRepository.getInstance(app);
    }

    public void initUserInfo() {
        String userId = userRepository.getCurrentUser().getValue().getUid();
        userInfoRepository.init(userId);
    }

    public LiveData<UserInfo> getCurrentUserInto() {
        return userInfoRepository.getUserInfo();
    }

    public String getGreenHouseId() {
        return userInfoRepository.getUserInfo().getValue().getGreenhouseID();
    }

    public void saveGreenHouseId(String id) {
        userInfoRepository.saveGreenhouseID(id);
    }

    public void subscribeToGreenhouse(String greenhouseID) {
        messagingService.get().subscribeToGreenhouse(greenhouseID);
    }

    public void unsubscribeFromGreenhouse(String greenhouseId) {
        messagingService.get().unsubscribeFromGreenhouse(greenhouseId);
    }

    public void clearCachedDate() {
        executorService.execute(() -> database.clearAllTables());
    }
}
