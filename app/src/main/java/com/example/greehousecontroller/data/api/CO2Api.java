package com.example.greehousecontroller.data.api;

import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Threshold;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface CO2Api {
    @GET("DioxideCarbon/{greenhouseId}?latest=true")
    Call<List<CO2>> getLatestCO2(@Path("greenhouseId") String greenhouseId);

    //thresholds
    @GET("threshold/{greenhouseid}/co2")
    Call<Threshold> getCo2Thresholds(@Path("greenhouseid") String greenhouseId);

    @PATCH("threshold/{greenhouseid}/co2")
    Call<Threshold> setCo2Thresholds(@Path("greenhouseid") String greenhouseId, @Body Threshold threshold);

}
