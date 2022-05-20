package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.api.TemperatureApi;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.model.Threshold;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TemperatureRepository {
    private static TemperatureRepository instance;
    private final Application app;
    private MutableLiveData<Temperature> latest;
    private MutableLiveData<ArrayList<Temperature>> historical;
    private MutableLiveData<Threshold> threshold;

    private TemperatureRepository(Application app){
        this.app = app;
        latest = new MutableLiveData<>(new Temperature());
        threshold = new MutableLiveData<>(new Threshold());
        historical = new MutableLiveData<>(new ArrayList<Temperature>());

        // TODO: Store latest measurement in phones storage
        //  so that if connection fails, latest received date is shown
    }

    public static TemperatureRepository getInstance(Application app){
        if (instance == null){
            instance = new TemperatureRepository(app);
        }
        return instance;
    }

    public MutableLiveData<Temperature> getLatest() {
        return latest;
    }
    public MutableLiveData<ArrayList<Temperature>> getTemperatureHistoryData() {
        return historical;
    }
    public void updateHistoricalMeasurement(String greenhouseId){
        TemperatureApi temperatureApi = ServiceGenerator.getTemperatureAPI();
        Call<ArrayList<Temperature>> call = temperatureApi.getHistoricalTemperature(greenhouseId);
        call.enqueue(new Callback<ArrayList<Temperature>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ArrayList<Temperature>> call, Response<ArrayList<Temperature>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-temp-ulm", response.body().toString());
                    historical.setValue(response.body());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ArrayList<Temperature>> call, Throwable t) {
                Log.e("Api-temp-ulm",t.getMessage());
            }
        });
    }

    public MutableLiveData<Threshold> getThreshold(){
        return threshold;
    }

    public void updateLatestMeasurement(String greenhouseId){
        TemperatureApi temperatureApi = ServiceGenerator.getTemperatureAPI();
        Call<List<Temperature>> call = temperatureApi.getLatestTemperature(greenhouseId);
        call.enqueue(new Callback<List<Temperature>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Temperature>> call, Response<List<Temperature>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-temp-ulm", response.body().toString());
                    latest.setValue(response.body().get(0));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Temperature>> call, Throwable t) {
                Log.e("Api-temp-ulm",t.getMessage());
            }
        });
    }

    public void updateThreshold(String greenhouseId){
        TemperatureApi temperatureApi = ServiceGenerator.getTemperatureAPI();
        Call<Threshold> call = temperatureApi.getTemperatureThresholds(greenhouseId);
        call.enqueue(new Callback<Threshold>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Threshold> call, Response<Threshold> response) {
                if (response.isSuccessful()){
                    Log.i("Api-temp-ut", response.body().toString());
                    threshold.setValue(response.body());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-temp-ut",t.getMessage());
            }
        });
    }

    public void setThreshold(String greenhouseId, Threshold newThreshold){
        TemperatureApi temperatureApi = ServiceGenerator.getTemperatureAPI();
        Call<Threshold> call = temperatureApi.setTemperatureThresholds(greenhouseId, newThreshold);
        call.enqueue(new Callback<Threshold>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Threshold> call, Response<Threshold> response) {
                if (response.isSuccessful()){
                    Log.i("Api-temp-st", response.body().toString());
                    threshold.setValue(response.body());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-temp-st",t.getMessage());
            }
        });
    }
}
