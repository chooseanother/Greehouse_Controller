package com.example.greehousecontroller.data.repository;

import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.model.UserInfoLiveData;
import com.example.greehousecontroller.utils.Config;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfoRepository {
    private static UserInfoRepository instance;
    private DatabaseReference dbRef;
    private UserInfoLiveData userInfo;

    private UserInfoRepository() {
    }

    public static synchronized UserInfoRepository getInstance() {
        if (instance == null)
            instance = new UserInfoRepository();
        return instance;
    }

    public void init(String userId) {
        dbRef = FirebaseDatabase.getInstance(Config.FIREBASE_DB_URL).getReference().child("users").child(userId);
        userInfo = new UserInfoLiveData(dbRef);
    }

    public LiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void saveGreenhouseID(String greenhouseID) {
        dbRef.child("greenhouseID").setValue(greenhouseID);
    }
}
