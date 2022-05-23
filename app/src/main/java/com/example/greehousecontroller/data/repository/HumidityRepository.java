package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.HumidityApi;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Threshold;

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

    private HumidityRepository(Application app){
        this.app = app;
        latest = new MutableLiveData<>(new Humidity());
        threshold = new MutableLiveData<>(new Threshold());
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

    public void updateLatestMeasurement(String greenhouseId){
        HumidityApi humidityApi = ServiceGenerator.getHumidityAPI();
        Call<List<Humidity>> call = humidityApi.getLatestHumidity(greenhouseId);
        call.enqueue(new Callback<List<Humidity>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Humidity>> call, Response<List<Humidity>> response) {
                if (response.isSuccessful()){
                    if(response.body() != null){
                        Log.i("Api-hum-ulm", response.body().toString());
                        latest.setValue(response.body().get(0));
                    }
                }

                if(!response.isSuccessful()){
                        Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_measurements, Toast.LENGTH_SHORT);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Humidity>> call, Throwable t) {
                Log.e("Api-hum-ulm",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
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
                    if(response.body() != null){
                        Log.i("Api-hum-ut", response.body().toString());
                        threshold.setValue(response.body());
                    }
                }

                if(!response.isSuccessful()){
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_threshold, Toast.LENGTH_SHORT);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-hum-ut",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
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
                    if(response.body() != null){
                        Log.i("Api-hum-st", response.body().toString());
                        threshold.setValue(response.body());
                    }
                }

                if(!response.isSuccessful()){
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_access_server, Toast.LENGTH_SHORT);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-hum-st",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
            }
        });
    }
}
