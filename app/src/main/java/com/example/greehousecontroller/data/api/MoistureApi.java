package com.example.greehousecontroller.data.api;

import com.example.greehousecontroller.data.model.Moisture;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoistureApi {
    @GET("Moisture/{greenhouseId}/{potId}")
    Call<List<Moisture>> getHistoricalMoisture(@Path("greenhouseId") String greenhouseId, @Path("potId") int potId);

    @GET("Moisture/{greenhouseId}/{potId}?latest=true")
    Call<List<Moisture>> getLatestMoisture(@Path("greenhouseId") String greenhouseId, @Path("potId") int potId);
}
