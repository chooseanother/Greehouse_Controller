package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.UserInfo;
import com.example.greehousecontroller.data.repository.CO2Repository;
import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;
import com.example.greehousecontroller.data.repository.UserInfoRepository;
import com.example.greehousecontroller.data.repository.UserRepository;
import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import java.util.Objects;

public class GraphsViewModel extends AndroidViewModel {

    public GraphsViewModel(Application application){
        super(application);
    }
}