package com.example.greehousecontroller.ui.login_register;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.greehousecontroller.Repository.UserRepository.UserRepository;

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