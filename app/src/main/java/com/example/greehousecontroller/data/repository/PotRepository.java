package com.example.greehousecontroller.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.api.PotAPI;
import com.example.greehousecontroller.data.model.Pot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PotRepository {
    private MutableLiveData<List<Pot>> pots;
    private MutableLiveData<Pot> currentPot;
    private static PotRepository instance;
    private final Application app;
    private boolean result;

    private PotRepository(Application app){
        this.app = app;
        pots = new MutableLiveData<>();
        currentPot = new MutableLiveData<>();
    }

    public static synchronized PotRepository getInstance(Application app){
        if(instance == null){
            instance = new PotRepository(app);
        }
        return  instance;
    }

    public void init(String greenHouseId, int potId){
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Call<Pot> call = potAPI.getPotDetailsById(greenHouseId, potId);
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if(response.isSuccessful()){
                    Log.i("Api-pot-ulm", response.body().toString());
                    currentPot.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Log.e("Api-pot-ulm",t.getMessage());
            }
        });
    }

    public MutableLiveData<List<Pot>> getPots() {
        return pots;
    }

    public MutableLiveData<Pot> getCurrentPot(){
        return currentPot;
    }

    public void updateCurrentPot(String greenHouseId, int potId, String name, double minimumThreshold) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Pot pot = new Pot(potId, name, minimumThreshold);
        Call<Pot> call = potAPI.updatePotDetailsById(greenHouseId, potId, pot);
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if(response.isSuccessful()) {
                    Log.i("Api-pot-ulm", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Log.e("Api-pot-ulm",t.getMessage());
            }
        });
        System.out.println("THE RESULT IS : " + result);
    }

    public void addPot(String greenhouseId, String name, String minimumMoistureThreshold) {
       PotAPI potAPI = ServiceGenerator.getPotAPI();
       Pot pot = new Pot(name, 0, Double.valueOf(minimumMoistureThreshold));
       Call<Pot> call = potAPI.addPotDetailsById(greenhouseId, pot);
       call.enqueue(new Callback<Pot>() {
           @Override
           public void onResponse(Call<Pot> call, Response<Pot> response) {
                   Log.i("Api-hum-ulm", response.body().toString());
                   result = true;
           }

           @Override
           public void onFailure(Call<Pot> call, Throwable t) {
               Log.e("Api-pot-ulm",t.getMessage());
           }
       });
    }

    public void updateLatestMeasurement(String greenhouseId) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Call<List<Pot>> call = potAPI.getAllPotsByGreenhouseId(greenhouseId);
        call.enqueue(new Callback<List<Pot>>() {
            @Override
            public void onResponse(Call<List<Pot>> call, Response<List<Pot>> response) {
                if(response.isSuccessful()){
                    Log.i("Api-pot-ulm", response.body().toString());
                    pots.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Pot>> call, Throwable t) {
                Log.e("Api-pot-ulm",t.getMessage());
            }
        });
    }
}
