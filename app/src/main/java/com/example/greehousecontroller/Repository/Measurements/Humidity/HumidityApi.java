package com.example.greehousecontroller.Repository.Measurements.Humidity;

import com.example.greehousecontroller.model.Humidity;
import com.example.greehousecontroller.model.Temperature;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HumidityApi {
    @GET("Humidity/{greenhouseId}")
    Call<List<Humidity>> getLatestHumidity(@Path("greenhouseId") String greenhouseId);
}
