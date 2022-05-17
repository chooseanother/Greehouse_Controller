package com.example.greehousecontroller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivityViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public LogInActivityViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }


}
