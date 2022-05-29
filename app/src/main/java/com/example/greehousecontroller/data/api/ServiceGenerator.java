package com.example.greehousecontroller.data.api;



import com.example.greehousecontroller.utils.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static TemperatureApi temperatureApi;
    private static HumidityApi humidityApi;
    private static PotAPI potAPI;
    private static CO2Api co2Api;
    private static MoistureApi moistureApi;
    private static SensorApi sensorApi;

    public static TemperatureApi getTemperatureAPI() {
        if (temperatureApi == null) {
            temperatureApi = new Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TemperatureApi.class);
        }
        return temperatureApi;
    }

    public static HumidityApi getHumidityAPI() {
        if (humidityApi == null) {
            humidityApi = new Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(HumidityApi.class);
        }
        return humidityApi;
    }

    public static MoistureApi getMoistureAPI() {
        if (moistureApi == null) {
            moistureApi = new Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MoistureApi.class);
        }
        return moistureApi;
    }

    public static PotAPI getPotAPI() {
        if (potAPI == null) {
            potAPI = new Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PotAPI.class);
        }
        return potAPI;
    }
    public static CO2Api getCO2Api(){
        if (co2Api == null){
            co2Api = new Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CO2Api.class);
        }
        return co2Api;
    }

    public static SensorApi getSensorApi(){
        if(sensorApi == null){
            sensorApi = new Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SensorApi.class);
        }
        return sensorApi;
    }

}

