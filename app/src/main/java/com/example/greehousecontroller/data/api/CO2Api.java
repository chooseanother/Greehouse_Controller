package com.example.greehousecontroller.data.api;

import com.example.greehousecontroller.data.model.CO2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CO2Api {
    @GET("co2/{greenhouseId}?latest=true")
    Call<List<CO2>> getLatestCO2(@Path("greenhouseId") String greenhouseId);
}