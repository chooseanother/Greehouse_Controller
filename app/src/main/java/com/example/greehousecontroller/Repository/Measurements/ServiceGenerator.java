package com.example.greehousecontroller.Repository.Measurements;

import com.example.greehousecontroller.Repository.Measurements.Temperature.TemperatureApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static TemperatureApi temperatureApi;

    public static TemperatureApi getTemperatureAPI(){
        if (temperatureApi == null){
            temperatureApi = new Retrofit.Builder()
                    .baseUrl("http://sepdemo2-env.eba-n9rqip8w.eu-central-1.elasticbeanstalk.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TemperatureApi.class);
        }
        return temperatureApi;
    }
}

