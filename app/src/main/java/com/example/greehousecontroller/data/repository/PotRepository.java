package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.PotAPI;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.dao.PotDAO;
import com.example.greehousecontroller.data.database.AppDatabase;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.utils.RepositoryCallback;
import com.example.greehousecontroller.utils.ToastMaker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PotRepository {
    private MutableLiveData<List<Pot>> pots;
    private MutableLiveData<Pot> currentPot;
    private final PotDAO potDAO;
    private static PotRepository instance;
    private final Application app;
    private final ExecutorService executorService;
    private ToastMaker toastMaker;

    private PotRepository(Application app) {
        this.app = app;
        AppDatabase appDatabase = AppDatabase.getInstance(app);
        executorService = Executors.newFixedThreadPool(2);
        potDAO = appDatabase.potDAO();
        toastMaker = ToastMaker.getInstance();
        pots = new MutableLiveData<>(new ArrayList<>());

        currentPot = new MutableLiveData<>();
    }

    public static synchronized PotRepository getInstance(Application app) {
        if (instance == null) {
            instance = new PotRepository(app);
        }
        return instance;
    }

    public void loadCachedData(){
        executorService.execute(() -> {
            List<Pot> allPots = potDAO.getAll();
            if (allPots == null || allPots.isEmpty()) {
                pots.postValue(new ArrayList<>());
            } else {
                pots.postValue(allPots);
            }
        });
    }

    public void init(String greenHouseId, int potId) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Call<Pot> call = potAPI.getPotDetailsById(greenHouseId, potId);
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("Api-pot-ulm", response.body().toString());
                        currentPot.setValue(response.body());
                    }
                }

                if (!response.isSuccessful()) {
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_pot_details));
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Log.e("Api-pot-ulm", t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }

    public MutableLiveData<List<Pot>> getPots() {
        return pots;
    }

    public MutableLiveData<Pot> getCurrentPot() {
        return currentPot;
    }

    public void updateCurrentPot(String greenHouseId, int potId, String name, int moistureSensorId, double minimumThreshold) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Pot pot = new Pot(name, moistureSensorId, minimumThreshold);
        Log.i("Api-pot-update-info", "pot string: "+pot + " pot json: " + new Gson().toJson(pot) + " potId " + potId);
        Call<Void> call = potAPI.updatePotDetailsById(greenHouseId, potId, pot);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("Api-pot-ulm", response.body().toString());
                    }
                }

                if (!response.isSuccessful()) {
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_update_pot));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Api-pot-ulm", t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }

    public void addPot(String greenhouseId, int moistureSensorId, String name, double minimumMoistureThreshold) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Pot pot = new Pot(0, moistureSensorId, name, minimumMoistureThreshold);
        Log.i("Api-pot-add-new", new Gson().toJson(pot));
        Call<Void> call = potAPI.addPotDetailsByGreenhouseId(greenhouseId, pot);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("Api-pot-add-(:", response.body().toString());
                    }
                }

                if (!response.isSuccessful()) {
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_add_pot));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Api-pot-add-):", t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }

    public void updateLatestMeasurement(String greenhouseId, RepositoryCallback callback) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Call<List<Pot>> call = potAPI.getAllPotsByGreenhouseId(greenhouseId);
        call.enqueue(new Callback<List<Pot>>() {
            @Override
            public void onResponse(Call<List<Pot>> call, Response<List<Pot>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("Api-pot-getallpots-(:", response.body().toString());
                        pots.setValue(response.body());
                        executorService.execute(() -> {
                            potDAO.delete();
                            potDAO.insert(response.body());
                        });
                    }
                }

                if (!response.isSuccessful()) {
                    toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.unable_to_retrieve_measurements));
                    if (callback != null){
                        callback.call();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pot>> call, Throwable t) {
                Log.e("Api-pot-getallpots-):", t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
                if (callback != null){
                    callback.call();
                }
            }
        });
    }
}
