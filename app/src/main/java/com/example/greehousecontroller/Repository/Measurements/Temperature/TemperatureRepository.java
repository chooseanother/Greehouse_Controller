package com.example.greehousecontroller.Repository.Measurements.Temperature;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.model.Temperature;
import com.example.greehousecontroller.Repository.Measurements.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TemperatureRepository {
    private static TemperatureRepository instance;
    private final Application app;
    private MutableLiveData<Temperature> latest;

    private TemperatureRepository(Application app){
        this.app = app;
        latest = new MutableLiveData<>(new Temperature());
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

    public void updateLatestMeasurement(String greenhouseId){
        TemperatureApi temperatureApi = ServiceGenerator.getTemperatureAPI();
        Call<List<Temperature>> call = temperatureApi.getLatestTemperature(greenhouseId);
        call.enqueue(new Callback<List<Temperature>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Temperature>> call, Response<List<Temperature>> response) {
                if (response.isSuccessful()){
                    latest.setValue(response.body().get(0));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Temperature>> call, Throwable t) {
                Log.e("Api",t.getMessage());
            }
        });
    }
}
