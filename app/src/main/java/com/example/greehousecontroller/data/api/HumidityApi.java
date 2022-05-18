package com.example.greehousecontroller.data.api;




import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Threshold;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface HumidityApi {
    @GET("Humidity/{greenhouseId}?latest=true")
    Call<List<Humidity>> getLatestHumidity(@Path("greenhouseId") String greenhouseId);

    //thresholds
    @GET("threshold/{greenhouseid}/humidity")
    Call<Threshold> getHumidityThresholds(@Path("greenhouseid") String greenhouseId);
    @PATCH("threshold/{greenhouseid}/humidity")
    Call<Threshold> setHumidityThresholds(@Path("greenhouseid") String greenhouseId, @Body Threshold threshold);

}
