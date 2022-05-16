package com.example.greehousecontroller.data.api;

import com.example.greehousecontroller.data.model.Temperature;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TemperatureApi {
    @GET("Temperature/{greenhouseid}?latest=true")
    Call<List<Temperature>> getLatestTemperature(@Path("greenhouseid") String greenhouseId);


    @GET("Temperature/{greenhouseid}")
    Call<ArrayList<Temperature>> getHistoricalTemperature(@Path("greenhouseid") String greenhouseId);
}
