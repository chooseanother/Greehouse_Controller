package com.example.greehousecontroller.Repository.Measurements.Humidity;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.Repository.Measurements.ServiceGenerator;
import com.example.greehousecontroller.model.Humidity;
import com.example.greehousecontroller.model.Pot;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class HumidityRepository {
    private static HumidityRepository instance;
    private final Application app;
    private MutableLiveData<Humidity> latest;

    private HumidityRepository(Application app){
        this.app = app;
        latest = new MutableLiveData<>(new Humidity());
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

    public void updateLatestMeasurement(String greenhouseId){
        HumidityApi humidityApi = ServiceGenerator.getHumidityApiAPI();
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
}
