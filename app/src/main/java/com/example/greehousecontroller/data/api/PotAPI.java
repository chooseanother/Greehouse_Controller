package com.example.greehousecontroller.data.api;

import com.example.greehousecontroller.data.model.Pot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PotAPI {
    @GET("Pot/{greenhouseId}/{PotId}")
    Call<Pot> getPotDetailsById(@Path("greenhouseId") String greenhouseId, @Path("PotId") int potId);

    @GET("Pot/{greenhouseId}")
    Call<List<Pot>> getAllPotsByGreenhouseId(@Path("greenhouseId") String greenhouseId);

    @PATCH("Pot/{greenhouseId}/{PotId}")
    Call updatePotDetailsById(@Path("greenhouseId") String greenhouseId, @Path("PotId") int potId, String name, double minimumThreshold);

    @POST("Pot/{greenhouseId}")
    Call addPotDetailsById(@Path("greenhouseId") String greenhouseId, Pot pot);
}
