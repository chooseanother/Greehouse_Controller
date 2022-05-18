package com.example.greehousecontroller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.data.repository.GreenHouseRepository;
import com.example.greehousecontroller.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class GreenHouseIdActivityViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private GreenHouseRepository greenHouseRepository;

    public GreenHouseIdActivityViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public boolean getGreenHouseId()
    {
        return greenHouseRepository.getGreenHouseId();
    }

    public void logOut(){
        userRepository.signOut();
    }

    public void saveGreenHouseId(String userEmail,String id){
        userRepository.saveGreenHouseId(userEmail,id);
    }
}
