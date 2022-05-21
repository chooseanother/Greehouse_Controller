package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.repository.CO2Repository;
import com.example.greehousecontroller.data.repository.HumidityRepository;
import com.example.greehousecontroller.data.repository.TemperatureRepository;

import java.util.ArrayList;

public class GraphsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private TemperatureRepository temperatureRepository;
    private CO2Repository co2Repository;
    private HumidityRepository humidityRepository;

    public GraphsViewModel(Application application){
        super(application);
        temperatureRepository = TemperatureRepository.getInstance(application);
        co2Repository = CO2Repository.getInstance(application);
        humidityRepository = HumidityRepository.getInstance(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is graphs fragment");
    }
    public LiveData<ArrayList<Temperature>> getTemperatureHistoryData() {
        return temperatureRepository.getTemperatureHistoryData();
    }
    public LiveData<ArrayList<CO2>> getCo2HistoryData() {
        return co2Repository.getCo2HistoryData();
    }
    public MutableLiveData<CO2> getLatestCO2(){
        return co2Repository.getLatest();
    }

    public MutableLiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Temperature> getLatestTemperature(){
        return temperatureRepository.getLatest();
    }

    public MutableLiveData<Humidity> getLatestHumidity(){
        return humidityRepository.getLatest();
    }

    public LiveData<ArrayList<Humidity>> getHumidityHistoryData() {
        return humidityRepository.getHistoricalData();
    }

    public void updateHistoryData(String greenHouseId)
    {
        temperatureRepository.updateHistoricalData(greenHouseId);
        co2Repository.updateHistoricalData(greenHouseId);
        humidityRepository.updateHistoricalData(greenHouseId);
    }
}