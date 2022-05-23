package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.api.HumidityApi;
import com.example.greehousecontroller.data.api.TemperatureApi;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Threshold;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class HumidityRepository {
    private static HumidityRepository instance;
    private final Application app;
    private MutableLiveData<Humidity> latest;
    private MutableLiveData<Threshold> threshold;
    private MutableLiveData<ArrayList<Humidity>> history;


    private HumidityRepository(Application app){
        this.app = app;
        latest = new MutableLiveData<>(new Humidity());
        threshold = new MutableLiveData<>(new Threshold());
        history = new MutableLiveData<>(new ArrayList<>());
    }

    public static HumidityRepository getInstance(Application app){
        if (instance == null){
            instance = new HumidityRepository(app);
        }
        return instance;
    }

    public MutableLiveData<Humidity> getLatest() {
        return latest;
    }


    public MutableLiveData<Threshold> getThreshold(){
        return threshold;
    }

    public MutableLiveData<ArrayList<Humidity>> getHistoricalData(){
        return history;
    }
    public void updateHistoricalData(String greenhouseId) {
        HumidityApi humidityApi = ServiceGenerator.getHumidityAPI();
        Call<ArrayList<Humidity>> call = humidityApi.getHistoricalHumidity(greenhouseId);
        call.enqueue(new Callback<ArrayList<Humidity>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ArrayList<Humidity>> call, Response<ArrayList<Humidity>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-hum-hist", response.body().toString());
                    history.setValue(response.body());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ArrayList<Humidity>> call, Throwable t) {
                Log.e("Api-hum-hist",t.getMessage());
            }
        });
    }

    public void updateLatestMeasurement(String greenhouseId){
        HumidityApi humidityApi = ServiceGenerator.getHumidityAPI();
        Call<List<Humidity>> call = humidityApi.getLatestHumidity(greenhouseId);
        call.enqueue(new Callback<List<Humidity>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Humidity>> call, Response<List<Humidity>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-hum-ulm", response.body().toString());
                    latest.setValue(response.body().get(0));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Humidity>> call, Throwable t) {
                Log.e("Api-hum-ulm",t.getMessage());
            }
        });
    }

    public void updateThreshold(String greenhouseId){
        HumidityApi humidityApi = ServiceGenerator.getHumidityAPI();
        Call<Threshold> call = humidityApi.getHumidityThresholds(greenhouseId);
        call.enqueue(new Callback<Threshold>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Threshold> call, Response<Threshold> response) {
                if (response.isSuccessful()){
                    Log.i("Api-hum-ut", response.body().toString());
                    threshold.setValue(response.body());
                }
                else{
                    Log.i("Api-hum-ut", response.toString());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-hum-ut",t.getMessage());
            }
        });
    }

    public void setThreshold(String greenhouseId, Threshold newThreshold){
        HumidityApi humidityApi = ServiceGenerator.getHumidityAPI();
        Call<Threshold> call = humidityApi.setHumidityThresholds(greenhouseId, newThreshold);
        call.enqueue(new Callback<Threshold>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Threshold> call, Response<Threshold> response) {
                if (response.isSuccessful()){
                    Log.i("Api-hum-st", response.body().toString());
                    threshold.setValue(response.body());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-hum-st",t.getMessage());
            }
        });
    }
}
