package com.example.greehousecontroller.data.api;

import com.example.greehousecontroller.data.model.Sensor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SensorApi {
    @GET("Greenhouse/{greenhouseid}/SensorStatus")
    Call<List<Sensor>> getSensorStatus(@Path("greenhouseid") String greenhouseId);
}
