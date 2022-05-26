package com.example.greehousecontroller.data.repository;
import android.app.Application;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.api.MoistureApi;
import com.example.greehousecontroller.data.api.ServiceGenerator;
import com.example.greehousecontroller.data.database.AppDatabase;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Moisture;
import com.example.greehousecontroller.utils.ToastMaker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MoistureRepository {

    private static MoistureRepository instance;
    private final Application app;
    private MutableLiveData<Moisture> latest;
    private MutableLiveData<List<Moisture>> history;
    private final ExecutorService executorService;
    private ToastMaker toastMaker;



    private MoistureRepository(Application app){
        this.app = app;
        AppDatabase appDatabase = AppDatabase.getInstance(app);
        executorService = Executors.newFixedThreadPool(4);
        latest = new MutableLiveData<>();
        history = new MutableLiveData<>();
        toastMaker = ToastMaker.getInstance();

    }

    public static MoistureRepository getInstance(Application app){
        if (instance == null){
            instance = new MoistureRepository(app);
        }
        return instance;
    }

    public void updateHistoricalData(String greenhouseId,int potId) {
        history.setValue(new ArrayList<Moisture>());
        MoistureApi moistureApi = ServiceGenerator.getMoistureAPI();
        Call<List<Moisture>> call = moistureApi.getHistoricalMoisture(greenhouseId,potId);
        call.enqueue(new Callback<List<Moisture>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Moisture>> call, Response<List<Moisture>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-moist-hist", response.body().toString());
                    history.setValue(response.body());
                }
            }
               /*executorService.execute(()->{
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

            }*/
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Moisture>> call, Throwable t) {
                Log.e("Api-moist-hist",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }

        });
    }
    public void updateLatestData(String greenhouseId,int potId) {
        MoistureApi moistureApi = ServiceGenerator.getMoistureAPI();
        Call<List<Moisture>> call = moistureApi.getLatestMoisture(greenhouseId,potId);
        call.enqueue(new Callback<List<Moisture>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Moisture>> call, Response<List<Moisture>> response) {
                if (response.isSuccessful()){
                    Log.i("Api-moist-latest", response.body().toString());
                    Moisture result = response.body().get(0);
                    latest.setValue(result);
                }
            }
             /*executorService.execute(()->{
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

            }*/
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Moisture>> call, Throwable t) {
                Log.e("Api-moist-latest",t.getMessage());
                toastMaker.makeToast(app.getApplicationContext(), app.getString(R.string.connection_error));
            }
        });
    }

    public MutableLiveData<Moisture> getLatest() {
        return latest;
    }

    public MutableLiveData<List<Moisture>> getHistoricalData(){
        return history;
    }


}
