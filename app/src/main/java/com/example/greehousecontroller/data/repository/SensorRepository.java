package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.SensorApi;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.model.Sensor;
import com.example.greehousecontroller.utils.ToastMaker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorRepository {
    private static SensorRepository instance;
    private final Application application;
    private final MutableLiveData<List<Sensor>> sensors;
    private final ToastMaker toastMaker;

    private SensorRepository(Application application) {
        this.application = application;
        toastMaker = ToastMaker.getInstance();
        sensors = new MutableLiveData<>();
    }

    public static SensorRepository getInstance(Application application) {
        if (instance == null) {
            instance = new SensorRepository(application);
        }
        return instance;
    }

    public MutableLiveData<List<Sensor>> getSensors() {
        return sensors;
    }

    public void updateSensors(String greenhouseId) {
        SensorApi sensorApi = ServiceGenerator.getSensorApi();
        Call<List<Sensor>> call = sensorApi.getSensorStatus(greenhouseId);
        call.enqueue(new Callback<List<Sensor>>() {
            @Override
            public void onResponse(Call<List<Sensor>> call, Response<List<Sensor>> response) {
                if (response.isSuccessful()) {
                    Log.i("Api-sens-us", response.body().toString());
                    sensors.setValue(response.body());
                }
                if (!response.isSuccessful()) {
                    toastMaker.makeToast(application.getApplicationContext(), application.getString(R.string.unable_to_retrieve_sensor_status));
                }
            }

            @Override
            public void onFailure(Call<List<Sensor>> call, Throwable t) {
                Log.e("Api-sens-us", t.getMessage());
                toastMaker.makeToast(application.getApplicationContext(), application.getString(R.string.connection_error));
            }
        });
    }
}
