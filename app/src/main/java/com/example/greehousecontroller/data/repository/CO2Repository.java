package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.api.CO2Api;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.api.TemperatureApi;
import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Threshold;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CO2Repository {
    private static CO2Repository instance;
    private final Application app;
    private MutableLiveData<CO2> latest;
    private MutableLiveData<Threshold> threshold;
    private MutableLiveData<ArrayList<CO2>> history;

    private CO2Repository(Application app){
        this.app = app;
        latest = new MutableLiveData<>(new CO2());
        threshold = new MutableLiveData<>(new Threshold());
        history = new MutableLiveData<>(new ArrayList<>());
    }

    public static com.example.greehousecontroller.data.repository.CO2Repository getInstance(Application app){
        if (instance == null){
            instance = new com.example.greehousecontroller.data.repository.CO2Repository(app);
        }
        return instance;
    }

    public MutableLiveData<CO2> getLatest() {
        return latest;
    }

    public MutableLiveData<Threshold> getThreshold(){
        return threshold;
    }

    public void updateLatestMeasurement(String greenhouseId){
        CO2Api co2Api = ServiceGenerator.getCO2Api();
        Call<List<CO2>> call = co2Api.getLatestCO2(greenhouseId);
        call.enqueue(new Callback<List<CO2>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<CO2>> call, Response<List<CO2>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-co2-ulm", response.body().toString());
                    latest.setValue(response.body().get(0));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<CO2>> call, Throwable t) {
                Log.e("Api-co2-ulm",t.getMessage());
            }
        });
    }
    public MutableLiveData<ArrayList<CO2>> getCo2HistoryData(){
        return history;
    }
    public void updateHistoricalData(String greenhouseId){
        CO2Api co2Api = ServiceGenerator.getCO2Api();
        Call<ArrayList<CO2>> call = co2Api.getHistoricalCO2(greenhouseId);
        call.enqueue(new Callback<ArrayList<CO2>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ArrayList   <CO2>> call, Response<ArrayList<CO2>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-co2-hist", String.valueOf(response.body()));
                    history.setValue(response.body());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ArrayList<CO2>> call, Throwable t) {
                Log.e("Api-co2-hist",t.getMessage());
            }
        });
    }
    public void updateThreshold(String greenhouseId){
        CO2Api co2Api = ServiceGenerator.getCO2Api();
        Call<Threshold> call = co2Api.getCo2Thresholds(greenhouseId);
        call.enqueue(new Callback<Threshold>() {
            @retrofit2.internal.EverythingIsNonNull
            @Override
            public void onResponse(Call<Threshold> call, Response<Threshold> response) {
                if (response.isSuccessful()){
                    Log.i("Api-co2-ut", response.body().toString());
                    threshold.setValue(response.body());
                }
            }
            @retrofit2.internal.EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-co2-ut",t.getMessage());
            }
        });
    }

    public void setThreshold(String greenhouseId, Threshold newThreshold){
        CO2Api co2Api = ServiceGenerator.getCO2Api();
        Call<Threshold> call = co2Api.setCo2Thresholds(greenhouseId, newThreshold);
        call.enqueue(new Callback<Threshold>() {
            @retrofit2.internal.EverythingIsNonNull
            @Override
            public void onResponse(Call<Threshold> call, Response<Threshold> response) {
                if (response.isSuccessful()){
                    Log.i("Api-co2-st", response.body().toString());
                    threshold.setValue(response.body());
                }
            }
            @retrofit2.internal.EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-co2-st",t.getMessage());
            }
        });
    }
}