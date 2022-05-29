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
import com.example.greehousecontroller.utils.RepositoryCallback;
import com.example.greehousecontroller.utils.ToastMaker;

import java.time.chrono.HijrahChronology;
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
    private ToastMaker toastMaker;
    private final ExecutorService executorService;

    private HumidityRepository(Application app){
        this.app = app;
        AppDatabase database = AppDatabase.getInstance(app);
        executorService = Executors.newFixedThreadPool(4);
        humidityDAO = database.humidityDAO();
        thresholdDAO = database.thresholdDAO();
        toastMaker = ToastMaker.getInstance();

        loadCachedData();
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

    private void loadCachedData(){
        executorService.execute(()->{
            Humidity latestMeasurement = humidityDAO.getLatest();
            if(latestMeasurement == null){
                latest = new MutableLiveData<>();
            }
            else{
                latest = new MutableLiveData<>(latestMeasurement);
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

    public void updateHistoricalData(String greenhouseId,RepositoryCallback callback) {
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
                if(!response.isSuccessful()){
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_measurements));
                }

            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Humidity>> call, Throwable t) {
                Log.e("Api-hum-hist",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }

    public void updateLatestMeasurement(String greenhouseId, RepositoryCallback callback){
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
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_measurements));
                    if (callback != null){
                        callback.call();
                    }
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Humidity>> call, Throwable t) {
                Log.e("Api-hum-ulm",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
                if (callback != null){
                    callback.call();
                }
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
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_threshold));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-hum-ut",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
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
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_update_threshold));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-hum-st",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }

    public void resetLiveData(){
        latest = new MutableLiveData<>();
        threshold = new MutableLiveData<>(new Threshold("Humidity",0,0));
        history = new MutableLiveData<>();
    }
}
