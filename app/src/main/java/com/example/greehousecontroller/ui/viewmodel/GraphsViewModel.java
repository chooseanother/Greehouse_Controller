package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.repository.TemperatureRepository;

import java.util.ArrayList;

public class GraphsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private TemperatureRepository temperatureRepository;

    public GraphsViewModel(Application application){
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is graphs fragment");
    }
    public LiveData<ArrayList<Temperature>> getTemperatureHistoryData() {
        return temperatureRepository.getTemperatureHistoryData();
    }

    public MutableLiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Temperature> getLatestTemperature(){
        return temperatureRepository.getLatest();
    }


    public void updateTemperatureHistoryData(String greenHouseId)
    {
        temperatureRepository.updateHistoricalMeasurement(greenHouseId);

    }
}