package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.CO2Api;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.dao.CO2DAO;
import com.example.greehousecontroller.data.dao.ThresholdDAO;
import com.example.greehousecontroller.data.database.AppDatabase;
import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Threshold;
import com.example.greehousecontroller.utils.ToastMaker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CO2Repository {
    private static CO2Repository instance;
    private final Application app;
    private final CO2DAO co2DAO;
    private final ThresholdDAO thresholdDAO;
    private MutableLiveData<CO2> latest;
    private MutableLiveData<Threshold> threshold;
    private MutableLiveData<List<CO2>> history;
    private final ExecutorService executorService;
    private ToastMaker toastMaker;

    private CO2Repository(Application app){
        this.app = app;
        AppDatabase database = AppDatabase.getInstance(app);
        executorService = Executors.newFixedThreadPool(4);
        co2DAO = database.co2DAO();
        thresholdDAO = database.thresholdDAO();
        toastMaker = ToastMaker.getInstance();

        loadCachedData();
    }

    public static com.example.greehousecontroller.data.repository.CO2Repository getInstance(Application app){
        if (instance == null){
            instance = new CO2Repository(app);
        }
        return instance;
    }

    public MutableLiveData<CO2> getLatest() {
        return latest;
    }

    public MutableLiveData<Threshold> getThreshold(){
        return threshold;
    }

    private void loadCachedData(){
        executorService.execute(()->{
            if(co2DAO.getAll() == null || co2DAO.getAll().isEmpty()){
                latest = new MutableLiveData<>();
            }
            else
            {
                latest = new MutableLiveData<>(co2DAO.getAll().get(co2DAO.getAll().size() -1));
            }
        });

        executorService.execute(()->{
            if(thresholdDAO.getThreshold("CO2") == null){
                threshold = new MutableLiveData<>(new Threshold("CO2", 0, 0));
            }
            else
            {
                threshold = new MutableLiveData<>(thresholdDAO.getThreshold("CO2"));
            }
        });

        executorService.execute(()->{
            if(co2DAO.getAll() == null){
                history = new MutableLiveData<>();
            }
            else{
                history = new MutableLiveData<>(co2DAO.getAll());
            }
        });
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
                        CO2 result = response.body().get(0);
                        latest.setValue(result);
                        executorService.execute(()->{
                        co2DAO.insert(response.body());
                        });
                    }
                }

                if(!response.isSuccessful()){
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_measurements));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<CO2>> call, Throwable t) {
                Log.e("Api-co2-ulm",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }

    public MutableLiveData<List<CO2>> getCo2HistoryData(){
        return history;
    }

    public void updateHistoricalData(String greenhouseId){
        CO2Api co2Api = ServiceGenerator.getCO2Api();
        Call<ArrayList<CO2>> call = co2Api.getHistoricalCO2(greenhouseId);
        call.enqueue(new Callback<ArrayList<CO2>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ArrayList<CO2>> call, Response<ArrayList<CO2>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-co2-hist", String.valueOf(response.body()));
                    history.setValue(response.body());
                    executorService.execute(()->{
                        ArrayList<CO2> result = response.body();
                            if(result.size() < 2000){
                                co2DAO.insert(response.body());
                            }
                            else{
                                co2DAO.insert(response.body().subList(0, 1999));
                            }
                    });
                }
                if(!response.isSuccessful()){
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_measurements));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ArrayList<CO2>> call, Throwable t) {
                Log.e("Api-co2-hist",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
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
                        executorService.execute(()-> {
                            if(thresholdDAO.getThreshold("CO2") == null){
                                Threshold threshold = new Threshold("CO2",response.body().getUpperThreshold(), response.body().getLowerThreshold());
                            thresholdDAO.insert(threshold);
                            }
                            else{
                                thresholdDAO.update("CO2", response.body().getUpperThreshold(), response.body().getLowerThreshold());
                            }
                        });
                    }
                }

                if(!response.isSuccessful()){
                        toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_threshold));
                    }
                }
            @retrofit2.internal.EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-co2-ut",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
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
                        executorService.execute(()-> {
                            if(thresholdDAO.getThreshold("CO2") == null){
                                Threshold threshold = new Threshold("CO2",response.body().getUpperThreshold(), response.body().getLowerThreshold());
                                thresholdDAO.insert(threshold);
                            }
                            else{
                                thresholdDAO.update("CO2", response.body().getUpperThreshold(), response.body().getLowerThreshold());
                            }
                        });
                    }
                }

                if(!response.isSuccessful()){
                        toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_update_threshold));
                    }
            }
            @retrofit2.internal.EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-co2-st",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }

    public void resetLiveData(){
        latest = new MutableLiveData<>();
        threshold = new MutableLiveData<>(new Threshold("CO2",0,0));
        history = new MutableLiveData<>();
    }
}