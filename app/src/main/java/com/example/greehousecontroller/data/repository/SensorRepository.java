package com.example.greehousecontroller.data.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.model.Sensor;

import java.util.List;

public class SensorRepository {
    private static SensorRepository instance;
    private final Application application;
    private MutableLiveData<List<Sensor>> sensors;

    private SensorRepository(Application application){
        this.application = application;
    }

}
