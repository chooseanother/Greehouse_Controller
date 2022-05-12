package com.example.greehousecontroller.Repository.PotRepository;

import com.example.greehousecontroller.model.Pot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PotAPI {
    @GET("Greenhouse/{greenhouseId}/Pot/{PotId}")
    Call<Pot> getPotDetailsById(@Path("greenhouseId") String greenhouseId, @Path("PotId") int potId);

    @PATCH("Greenhouse/{greenhouseId}/Pot/{PotId}")
    Call updatePotDetailsById(@Path("greenhouseId") String greenhouseId, @Path("PotId") int potId, String name, String minimumThreshold);

    @PUT("Greenhouse/{greenhouseId}/{Pot}")
    Call addPotDetailsById(@Path("greenhouseId") String greenhouseId, @Path("Pot") Pot pot);
}
