package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.greehousecontroller.data.repository.UserRepository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository userRepository;


    public LoginViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance();
    }
    public void init() {
        userRepository.init();
    }

}