package com.example.greehousecontroller.Repository.Measurements;

import com.example.greehousecontroller.Repository.Measurements.CO2.CO2Api;
import com.example.greehousecontroller.Repository.Measurements.Humidity.HumidityApi;
import com.example.greehousecontroller.Repository.Measurements.Temperature.TemperatureApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static TemperatureApi temperatureApi;
    private static HumidityApi humidityApi;
    private static CO2Api co2Api;


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

    public static HumidityApi getHumidityApiAPI(){
        if (humidityApi == null){
            humidityApi = new Retrofit.Builder()
                    .baseUrl("http://sepdemo2-env.eba-n9rqip8w.eu-central-1.elasticbeanstalk.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(HumidityApi.class);
        }
        return humidityApi;
    }
    public static CO2Api getCO2Api(){
        if (co2Api == null){
            co2Api = new Retrofit.Builder()
                    .baseUrl("http://sepdemo2-env.eba-n9rqip8w.eu-central-1.elasticbeanstalk.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CO2Api.class);
        }
        return co2Api;
    }
}

