package com.example.greehousecontroller.data.api;

import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.Threshold;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface TemperatureApi {
    @GET("Temperature/{greenhouseid}?latest=true")
    Call<List<Temperature>> getLatestTemperature(@Path("greenhouseid") String greenhouseId);
    //thresholds
    @GET("threshold/{greenhouseid}/temperature")
    Call<Threshold> getTemperatureThresholds(@Path("greenhouseid") String greenhouseId);
    @PATCH("threshold/{greenhouseid}/temperature")
    Call<Threshold> setTemperatureThresholds(@Path("greenhouseid") String greenhouseId, @Body Threshold threshold);


    @GET("Temperature/{greenhouseid}")
    Call<ArrayList<Temperature>> getHistoricalTemperature(@Path("greenhouseid") String greenhouseId);
}
