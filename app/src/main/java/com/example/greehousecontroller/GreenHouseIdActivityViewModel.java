package com.example.greehousecontroller;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;
import com.example.greehousecontroller.utils.GreenhouseFirebaseMessagingService;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class GreenHouseIdActivityViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final GreenhouseFirebaseMessagingService messagingService;

    public GreenHouseIdActivityViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        messagingService = new GreenhouseFirebaseMessagingService();
    }

    public void initUserInfo() {
        String userId = userRepository.getCurrentUser().getValue().getUid();
        userInfoRepository.init(userId);
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public String getGreenHouseId() {
        return userInfoRepository.getUserInfo().getValue().getGreenhouseID();
    }

    public void logOut() {
        userRepository.signOut();
    }

    public void saveGreenHouseId(String id) {
        userInfoRepository.saveGreenhouseID(id);
    }

    public void subscribeToGreenhouse(String greenhouseID) {
        messagingService.subscribeToGreenhouse(greenhouseID);
    }
}
