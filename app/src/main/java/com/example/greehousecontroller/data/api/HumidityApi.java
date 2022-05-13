package com.example.greehousecontroller.data.api;




import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HumidityApi {
    @GET("Humidity/{greenhouseId}?latest=true")
    Call<List<Humidity>> getLatestHumidity(@Path("greenhouseId") String greenhouseId);
}
