package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.api.TemperatureApi;
import com.example.greehousecontroller.data.dao.TemperatureDAO;
import com.example.greehousecontroller.data.dao.ThresholdDAO;
import com.example.greehousecontroller.data.database.AppDatabase;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.Threshold;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TemperatureRepository {
    private static TemperatureRepository instance;
    private final Application app;
    private final TemperatureDAO temperatureDAO;
    private final ThresholdDAO thresholdDAO;
    private MutableLiveData<Temperature> latest;
    private MutableLiveData<ArrayList<Temperature>> historical;
    private MutableLiveData<Threshold> threshold;

    private final ExecutorService executorService;

    private TemperatureRepository(Application app){
        this.app = app;
        AppDatabase appDatabase = AppDatabase.getInstance(app);
        executorService = Executors.newFixedThreadPool(2);
        temperatureDAO = appDatabase.temperatureDAO();
        thresholdDAO = appDatabase.thresholdDAO();

        executorService.execute(()->{
            if(temperatureDAO.getAll() == null || temperatureDAO.getAll().isEmpty()){
                latest = new MutableLiveData<>();
            }
            else{
                latest = new MutableLiveData<>(temperatureDAO.getAll().get(temperatureDAO.getAll().size() - 1));
            }

            if(thresholdDAO.getThreshold("Temperature") == null){
                threshold = new MutableLiveData<>(new Threshold("Temperature",0,0));
            }
            else{
                threshold = new MutableLiveData<>(thresholdDAO.getThreshold("Temperature"));
            }

            if(temperatureDAO.getAll() == null){
                historical = new MutableLiveData<>();
            }
            else{
                historical = new MutableLiveData<>((ArrayList<Temperature>) temperatureDAO.getAll());
            }
        });
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
                    executorService.execute(()-> {
                        if(temperatureDAO.getAll() == null || temperatureDAO.getAll().isEmpty()){
                            temperatureDAO.insert(response.body());
                        }
                        else {
                            temperatureDAO.update(response.body());
                        }
                    });
                }

                if(!response.isSuccessful()){
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_measurements, Toast.LENGTH_SHORT);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ArrayList<Temperature>> call, Throwable t) {
                Log.e("Api-temp-ulm",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
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
                    executorService.execute(()-> {
                        if(temperatureDAO.getAll() == null || temperatureDAO.getAll().isEmpty()){
                            temperatureDAO.insert((ArrayList<Temperature>)response.body());
                        }
                        else{
                            temperatureDAO.update((ArrayList<Temperature>) response.body());
                        }
                    });
                }

                if(!response.isSuccessful()){
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_measurements, Toast.LENGTH_SHORT);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Temperature>> call, Throwable t) {
                Log.e("Api-temp-ulm",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
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
                    executorService.execute(()-> {
                        if(thresholdDAO.getThreshold("Temperature") == null){
                            Threshold threshold = new Threshold("Temperature",response.body().getUpperThreshold(), response.body().getLowerThreshold());
                            thresholdDAO.insert(threshold);
                        }
                        else{
                            thresholdDAO.update("Temperature", response.body().getUpperThreshold(), response.body().getLowerThreshold());
                        }
                    });                }

                if(!response.isSuccessful()){
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_threshold, Toast.LENGTH_SHORT);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-temp-ut",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
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
                    executorService.execute(()-> {
                        if(thresholdDAO.getThreshold("Temperature") == null){
                            Threshold threshold = new Threshold("Temperature",response.body().getUpperThreshold(), response.body().getLowerThreshold());
                            thresholdDAO.insert(threshold);
                        }
                        else{
                            thresholdDAO.update("Temperature", response.body().getUpperThreshold(), response.body().getLowerThreshold());
                        }
                    });
                }

                if(!response.isSuccessful()){
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_update_threshold, Toast.LENGTH_SHORT);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-temp-st",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
            }
        });
    }
}
