package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.PotAPI;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.dao.PotDAO;
import com.example.greehousecontroller.data.database.AppDatabase;
import com.example.greehousecontroller.data.model.Pot;

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

    private PotRepository(Application app) {
        this.app = app;
        AppDatabase appDatabase = AppDatabase.getInstance(app);
        executorService = Executors.newFixedThreadPool(2);
        potDAO = appDatabase.potDAO();

        executorService.execute(() -> {
            if (potDAO.getAll() == null || potDAO.getAll().isEmpty()) {
                pots = new MutableLiveData<>();
            } else {
                pots = new MutableLiveData<>(potDAO.getAll());
            }
        });
        currentPot = new MutableLiveData<>();
    }

    public static synchronized PotRepository getInstance(Application app) {
        if (instance == null) {
            instance = new PotRepository(app);
        }
        return instance;
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
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_pot_details, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Log.e("Api-pot-ulm", t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
            }
        });
    }

    public MutableLiveData<List<Pot>> getPots() {
        return pots;
    }

    public MutableLiveData<Pot> getCurrentPot() {
        return currentPot;
    }

    public void updateCurrentPot(String greenHouseId, int potId, String name, double minimumThreshold) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Pot pot = new Pot(potId, name, minimumThreshold);
        Call<Pot> call = potAPI.updatePotDetailsById(greenHouseId, potId, pot);
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("Api-pot-ulm", response.body().toString());
                    }
                }

                if (!response.isSuccessful()) {
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_update_pot, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Log.e("Api-pot-ulm", t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
            }
        });
    }

    public void addPot(String greenhouseId, int sensorId, String name, double minimumMoistureThreshold) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Pot pot = new Pot(name, sensorId, 0, minimumMoistureThreshold);
        Call<Pot> call = potAPI.addPotDetailsById(greenhouseId, pot);
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("Api-hum-ulm", response.body().toString());
                    }
                }

                if (!response.isSuccessful()) {
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_add_pot, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Log.e("Api-pot-ulm", t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
            }
        });
    }

    public void updateLatestMeasurement(String greenhouseId) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Call<List<Pot>> call = potAPI.getAllPotsByGreenhouseId(greenhouseId);
        call.enqueue(new Callback<List<Pot>>() {
            @Override
            public void onResponse(Call<List<Pot>> call, Response<List<Pot>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("Api-pot-ulm", response.body().toString());
                        pots.setValue(response.body());
                        executorService.execute(() -> {
                            if (potDAO.getAll().isEmpty() || potDAO.getAll() == null) {
                                potDAO.insert(response.body());
                            } else {
                                potDAO.update(response.body());
                            }
                        });
                    }
                }

                if (!response.isSuccessful()) {
                    Toast.makeText(app.getApplicationContext(), R.string.unable_to_retrieve_measurements, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<List<Pot>> call, Throwable t) {
                Log.e("Api-pot-ulm", t.getMessage());
                Toast.makeText(app.getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT);
            }
        });
    }
}
