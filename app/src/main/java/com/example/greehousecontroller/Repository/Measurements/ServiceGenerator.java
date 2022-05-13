package com.example.greehousecontroller.Repository.Measurements;

import com.example.greehousecontroller.Repository.Measurements.Humidity.HumidityApi;
import com.example.greehousecontroller.Repository.Measurements.Temperature.TemperatureApi;
import com.example.greehousecontroller.Repository.PotRepository.PotAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static TemperatureApi temperatureApi;
    private static HumidityApi humidityApi;
    private static PotAPI potAPI;

    public static TemperatureApi getTemperatureAPI() {
        if (temperatureApi == null) {
            temperatureApi = new Retrofit.Builder()
                    .baseUrl("http://sepdemo2-env.eba-n9rqip8w.eu-central-1.elasticbeanstalk.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TemperatureApi.class);
        }
        return temperatureApi;
    }

    public static HumidityApi getHumidityAPI() {
        if (humidityApi == null) {
            humidityApi = new Retrofit.Builder()
                    .baseUrl("http://sepdemo2-env.eba-n9rqip8w.eu-central-1.elasticbeanstalk.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(HumidityApi.class);
        }
        return humidityApi;
    }

    public static PotAPI getPotAPI() {
        if (potAPI == null) {
            potAPI = new Retrofit.Builder()
                    .baseUrl("http://sepdemo2-env.eba-n9rqip8w.eu-central-1.elasticbeanstalk.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PotAPI.class);
        }
        return potAPI;
    }
}

