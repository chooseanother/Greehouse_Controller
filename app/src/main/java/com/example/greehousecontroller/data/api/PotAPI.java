package com.example.greehousecontroller.data.api;

import com.example.greehousecontroller.data.model.Pot;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PotAPI {
    @GET("Greenhouse/{greenhouseId}/Pot/{PotId}")
    Call<Pot> getPotDetailsById(@Path("greenhouseId") String greenhouseId, @Path("PotId") int potId);

    @GET("Greenhouse/{greenhouseId}/Pot")
    Call<ArrayList<Pot>> getAllPotsByGreenhouseId(@Path("greenhouseId") String greenhouseId);

    @PATCH("Greenhouse/{greenhouseId}/Pot/{PotId}")
    Call updatePotDetailsById(@Path("greenhouseId") String greenhouseId, @Path("PotId") int potId, String name, float minimumThreshold);

    @PUT("Greenhouse/{greenhouseId}/{Pot}")
    Call addPotDetailsById(@Path("greenhouseId") String greenhouseId, @Path("Pot") Pot pot);
}
