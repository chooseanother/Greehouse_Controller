package com.example.greehousecontroller.Repository.Measurements.Temperature;

import com.example.greehousecontroller.model.Temperature;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TemperatureApi {
    @GET("Temperature/{greenhouseid}?latest=true")
    Call<List<Temperature>> getLatestTemperature(@Path("greenhouseid") String greenhouseId);
}
