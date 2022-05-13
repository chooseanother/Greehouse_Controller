package com.example.greehousecontroller.data.api;




import com.example.greehousecontroller.data.model.Humidity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HumidityApi {
    @GET("Humidity/{greenhouseId}?latest=true")
    Call<List<Humidity>> getLatestHumidity(@Path("greenhouseId") String greenhouseId);
}
