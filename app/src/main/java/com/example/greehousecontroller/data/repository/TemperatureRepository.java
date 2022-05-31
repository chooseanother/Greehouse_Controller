package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.api.TemperatureApi;
import com.example.greehousecontroller.data.dao.TemperatureDAO;
import com.example.greehousecontroller.data.dao.ThresholdDAO;
import com.example.greehousecontroller.data.database.AppDatabase;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.Threshold;
import com.example.greehousecontroller.utils.RepositoryCallback;
import com.example.greehousecontroller.utils.ToastMaker;

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
    private final MutableLiveData<Temperature> latest;
    private final MutableLiveData<List<Temperature>> historical;
    private final MutableLiveData<Threshold> threshold;
    private final ToastMaker toastMaker;

    private final ExecutorService executorService;

    private TemperatureRepository(Application app) {
        this.app = app;
        AppDatabase appDatabase = AppDatabase.getInstance(app);
        executorService = Executors.newFixedThreadPool(4);
        temperatureDAO = appDatabase.temperatureDAO();
        thresholdDAO = appDatabase.thresholdDAO();
        toastMaker = ToastMaker.getInstance();
        latest = new MutableLiveData<>(new Temperature());
        threshold = new MutableLiveData<>(new Threshold("Temperature", 0, 0));
        historical = new MutableLiveData<>(new ArrayList<>());
    }

    public static TemperatureRepository getInstance(Application app) {
        if (instance == null) {
            instance = new TemperatureRepository(app);
        }
        return instance;
    }

    public void loadLatestCachedData() {
        executorService.execute(() -> {
            // Get latest
            Temperature latestMeasurement = temperatureDAO.getLatest();
            if (latestMeasurement == null) {
                latest.postValue(new Temperature());
            } else {
                latest.postValue(latestMeasurement);
            }
        });
    }

    public void loadThresholdCachedData() {
        executorService.execute(() -> {
            Threshold temperatureThreshold = thresholdDAO.getThreshold("Temperature");
            if (temperatureThreshold == null) {
                threshold.postValue(new Threshold("Temperature", 0, 0));
            } else {
                threshold.postValue(temperatureThreshold);
            }
        });
    }

    public void loadHistoricalCachedData() {
        executorService.execute(() -> {
            List<Temperature> temperatureHistory = temperatureDAO.getAll();
            if (temperatureHistory == null || temperatureHistory.isEmpty()) {
                historical.postValue(new ArrayList<>());
            } else {
                Log.i("History Temperature: ", temperatureHistory.toString());
                historical.postValue(temperatureHistory);
            }
        });
    }

    public MutableLiveData<Temperature> getLatest() {
        return latest;
    }

    public MutableLiveData<List<Temperature>> getTemperatureHistoryData() {
        return historical;
    }

    public void updateHistoricalMeasurement(String greenhouseId, RepositoryCallback callback) {
        TemperatureApi temperatureApi = ServiceGenerator.getTemperatureAPI();
        Call<List<Temperature>> call = temperatureApi.getHistoricalTemperature(greenhouseId);
        call.enqueue(new Callback<List<Temperature>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Temperature>> call, Response<List<Temperature>> response) {
                if (response.isSuccessful()) {
                    Log.i("Api-temp-ulm", response.body().toString());
                    historical.setValue(response.body());
                    executorService.execute(() -> {
                        ArrayList<Temperature> result = (ArrayList<Temperature>) response.body();
                        if (result.size() < 2000) {
                            temperatureDAO.delete();
                            temperatureDAO.insert(response.body());
                        } else {
                            temperatureDAO.delete();
                            temperatureDAO.insert(response.body().subList(0, 1999));
                        }
                    });
                }

                if (!response.isSuccessful()) {
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_measurements));
                    if (callback != null) {
                        callback.call();
                    }
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Temperature>> call, Throwable t) {
                Log.e("Api-temp-ulm", t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
                if (callback != null) {
                    callback.call();
                }
            }
        });
    }

    public MutableLiveData<Threshold> getThreshold() {
        return threshold;
    }

    public void updateLatestMeasurement(String greenhouseId, RepositoryCallback callback) {
        TemperatureApi temperatureApi = ServiceGenerator.getTemperatureAPI();
        Call<List<Temperature>> call = temperatureApi.getLatestTemperature(greenhouseId);
        call.enqueue(new Callback<List<Temperature>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Temperature>> call, Response<List<Temperature>> response) {
                if (response.isSuccessful()) {
                    Log.i("Api-temp-ulm", String.valueOf(response.body()));
                    Temperature result = response.body().get(0);
                    latest.setValue(result);
                    executorService.execute(() -> {
                        temperatureDAO.insert(response.body());
                        Log.i("Temp allUpdated: ", temperatureDAO.getAll().toString());
                    });
                }

                if (!response.isSuccessful()) {
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_measurements));
                    if (callback != null) {
                        callback.call();
                    }
                }

            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Temperature>> call, Throwable t) {
                Log.e("Api-temp-ulm", t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
                if (callback != null) {
                    callback.call();
                }
            }
        });
    }

    public void updateThreshold(String greenhouseId) {
        TemperatureApi temperatureApi = ServiceGenerator.getTemperatureAPI();
        Call<Threshold> call = temperatureApi.getTemperatureThresholds(greenhouseId);
        call.enqueue(new Callback<Threshold>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Threshold> call, Response<Threshold> response) {
                if (response.isSuccessful()) {
                    Log.i("Api-temp-ut", response.body().toString());
                    threshold.setValue(response.body());
                    executorService.execute(() -> {
                        Threshold threshold = new Threshold("Temperature", response.body().getUpperThreshold(), response.body().getLowerThreshold());
                        thresholdDAO.insert(threshold);
                    });
                }

                if (!response.isSuccessful()) {
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_threshold));
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {
                Log.e("Api-temp-ut", t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }

    public void setThreshold(String greenhouseId, Threshold newThreshold) {
        TemperatureApi temperatureApi = ServiceGenerator.getTemperatureAPI();
        Call<Void> call = temperatureApi.setTemperatureThresholds(greenhouseId, newThreshold);
        call.enqueue(new Callback<Void>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_update_threshold));
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Api-temp-st", t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }
}
