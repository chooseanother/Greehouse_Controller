package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.CO2Api;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Threshold;

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

    private CO2Repository(Application app){
        this.app = app;
        latest = new MutableLiveData<>(new CO2());
        threshold = new MutableLiveData<>(new Threshold());
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
                    if(response.body() != null){
                        Log.i("Api-co2-ulm", response.body().toString());
                        latest.setValue(response.body().get(0));
                    }
                }

                if(!response.isSuccessful()){
                        Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_measurements, Toast.LENGTH_SHORT);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<CO2>> call, Throwable t) {
                Log.e("Api-co2-ulm",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
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
                    if(response.body() != null){
                        Log.i("Api-co2-ut", response.body().toString());
                        threshold.setValue(response.body());
                    }
                }

                if(!response.isSuccessful()){
                        Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_threshold, Toast.LENGTH_SHORT);
                    }
                }
            @retrofit2.internal.EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-co2-ut",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
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
                    if(response.body() != null){
                        Log.i("Api-co2-st", response.body().toString());
                        threshold.setValue(response.body());
                    }
                }

                if(!response.isSuccessful()){
                        Toast.makeText(app.getApplicationContext(), R.string.unable_to_access_server, Toast.LENGTH_SHORT);
                    }
            }
            @retrofit2.internal.EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-co2-st",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
            }
        });
    }
}