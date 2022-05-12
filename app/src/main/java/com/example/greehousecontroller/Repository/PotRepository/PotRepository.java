package com.example.greehousecontroller.Repository.PotRepository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greehousecontroller.Repository.Measurements.ServiceGenerator;
import com.example.greehousecontroller.model.Pot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PotRepository {
    private MutableLiveData<Pot> currentPot;
    private static PotRepository instance;
    private boolean result;
    FirebaseDatabase database;
    DatabaseReference reference;

    private PotRepository(){
    }

    public static synchronized PotRepository getInstance(){
        if(instance == null){
            instance = new PotRepository();
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
                    Log.i("Api-hum-ulm", response.body().toString());
                    currentPot.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Log.e("Api-hum-ulm",t.getMessage());
            }
        });
    }

    public MutableLiveData<Pot> getCurrentPot() {
        return currentPot;
    }

    public boolean updateCurrentPot(String greenHouseId, int potId, String name, String minimumThreshold) {
        PotAPI potAPI = ServiceGenerator.getPotAPI();
        Call call = potAPI.updatePotDetailsById(greenHouseId, potId, name, minimumThreshold);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    Log.i("Api-hum-ulm", response.body().toString());
                    result = true;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                result = false;
            }
        });
        return result;
    }

    public boolean addPot(String greenhouseId, String name, String minimumHumidity) {
       PotAPI potAPI = ServiceGenerator.getPotAPI();
       Pot pot = new Pot(name, 0, Integer.valueOf(minimumHumidity));
       Call call = potAPI.addPotDetailsById(greenhouseId, pot);
       call.enqueue(new Callback() {
           @Override
           public void onResponse(Call call, Response response) {
               if(response.isSuccessful()) {
                   Log.i("Api-hum-ulm", response.body().toString());
                   result = true;
               }
           }

           @Override
           public void onFailure(Call call, Throwable t) {
               result = false;
           }
       });
        return result;
    }

}
