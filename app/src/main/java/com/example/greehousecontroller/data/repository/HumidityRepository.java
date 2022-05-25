package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.HumidityApi;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.dao.HumidityDAO;
import com.example.greehousecontroller.data.dao.ThresholdDAO;
import com.example.greehousecontroller.data.database.AppDatabase;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Threshold;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class HumidityRepository {
    private static HumidityRepository instance;
    private final Application app;
    private final HumidityDAO humidityDAO;
    private final ThresholdDAO thresholdDAO;
    private MutableLiveData<Humidity> latest;
    private MutableLiveData<Threshold> threshold;
    private MutableLiveData<List<Humidity>> history;

    private final ExecutorService executorService;

    private HumidityRepository(Application app){
        this.app = app;
        AppDatabase database = AppDatabase.getInstance(app);
        executorService = Executors.newFixedThreadPool(4);
        humidityDAO = database.humidityDAO();
        thresholdDAO = database.thresholdDAO();

        executorService.execute(()->{
            if(humidityDAO.getAll() == null || humidityDAO.getAll().isEmpty()){
                latest = new MutableLiveData<>();
            }
            else{
                latest = new MutableLiveData<>(humidityDAO.getAll().get(humidityDAO.getAll().size() -1));
            }
        });

        executorService.execute(()->{
            if(thresholdDAO.getThreshold("Humidity") == null){
                threshold = new MutableLiveData<>(new Threshold("Humidity", 0,0));
            }
            else{
                threshold = new MutableLiveData<>(thresholdDAO.getThreshold("Humidity"));
            }
        });

        executorService.execute(()->{
            if(humidityDAO.getAll() == null){
                history = new MutableLiveData<>();
            }
            else{
                Log.i("History Humidity: ",humidityDAO.getAll().toString());
                history = new MutableLiveData<>((ArrayList<Humidity>) humidityDAO.getAll());
            }
        });
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

    public MutableLiveData<List<Humidity>> getHistoricalData(){
        return history;
    }
    public void updateHistoricalData(String greenhouseId) {
        HumidityApi humidityApi = ServiceGenerator.getHumidityAPI();
        Call<List<Humidity>> call = humidityApi.getHistoricalHumidity(greenhouseId);
        call.enqueue(new Callback<List<Humidity>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Humidity>> call, Response<List<Humidity>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-hum-hist", response.body().toString());
                    history.setValue(response.body());
                    executorService.execute(()->{
                        ArrayList<Humidity> result = (ArrayList<Humidity>) response.body();
                            if(result.size() < 2000){
                                humidityDAO.delete();
                                humidityDAO.insert(response.body());
                            }
                            else{
                                humidityDAO.delete();
                                humidityDAO.insert(response.body().subList(0, 1999));
                            }
                    });
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Humidity>> call, Throwable t) {
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
                    if(response.body() != null){
                        Log.i("Api-hum-ulm", response.body().toString());
                        Humidity result = response.body().get(0);
                        latest.setValue(result);
                        executorService.execute(()->{
                          humidityDAO.insert(response.body());
                        });
                    }
                }

                if(!response.isSuccessful()){
                        Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_measurements, Toast.LENGTH_SHORT).show();
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Humidity>> call, Throwable t) {
                Log.e("Api-hum-ulm",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
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
                        executorService.execute(()-> {
                            if(thresholdDAO.getThreshold("Humidity") == null){
                                Threshold threshold = new Threshold("Humidity",response.body().getUpperThreshold(), response.body().getLowerThreshold());
                                thresholdDAO.insert(threshold);
                            }
                            else{
                                thresholdDAO.update("Humidity", response.body().getUpperThreshold(), response.body().getLowerThreshold());
                            }
                        });
                    }
                }

                if(!response.isSuccessful()){
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_threshold, Toast.LENGTH_SHORT).show();
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-hum-ut",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
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
                        executorService.execute(()-> {
                            if(thresholdDAO.getThreshold("Humidity") == null){
                                Threshold threshold = new Threshold("Humidity",response.body().getUpperThreshold(), response.body().getLowerThreshold());
                                thresholdDAO.insert(threshold);
                            }
                            else{
                                thresholdDAO.update("Humidity", response.body().getUpperThreshold(), response.body().getLowerThreshold());
                            }
                        });
                    }
                }

                if(!response.isSuccessful()){
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_update_threshold, Toast.LENGTH_SHORT).show();
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-hum-st",t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
