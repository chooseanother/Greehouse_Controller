package com.example.greehousecontroller.Repository.Measurements.CO2;
import android.app.Application;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.greehousecontroller.Repository.Measurements.ServiceGenerator;
import com.example.greehousecontroller.model.CO2;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class CO2Repository {
    private static com.example.greehousecontroller.Repository.Measurements.CO2.CO2Repository instance;
    private final Application app;
    private MutableLiveData<CO2> latest;

    private CO2Repository(Application app){
        this.app = app;
        latest = new MutableLiveData<>(new CO2());
    }

    public static com.example.greehousecontroller.Repository.Measurements.CO2.CO2Repository getInstance(Application app){
        if (instance == null){
            instance = new com.example.greehousecontroller.Repository.Measurements.CO2.CO2Repository(app);
        }
        return instance;
    }

    public MutableLiveData<CO2> getLatest() {
        return latest;
    }

    public void updateLatestMeasurement(String greenhouseId){
        CO2Api co2Api = ServiceGenerator.getCO2Api();
        Call<List<CO2>> call = co2Api.getLatestCO2(greenhouseId);
        call.enqueue(new Callback<List<CO2>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<CO2>> call, Response<List<CO2>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-co2-ulm", response.body().toString());
                    latest.setValue(response.body().get(0));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<CO2>> call, Throwable t) {
                Log.e("Api-co2-ulm",t.getMessage());
            }
        });
    }
}