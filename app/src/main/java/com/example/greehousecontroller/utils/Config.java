package com.example.greehousecontroller.utils;

public class Config {
    public static final String FIREBASE_DB_URL = "https://greehouse-controller-default-rtdb.europe-west1.firebasedatabase.app/";
    public static final String API_BASE_URL = "http://sepdemo2-env.eba-n9rqip8w.eu-central-1.elasticbeanstalk.com";
    public static final int MAX_NUMBER_OF_POTS = 6;

    // Threshold limits
    public static final double MAX_UPPER_THRESHOLD_TEMPERATURE = 60;
    public static final double MIN_LOWER_THRESHOLD_TEMPERATURE = -20;
    public static final double MAX_UPPER_THRESHOLD_HUMIDITY = 100;
    public static final double MIN_LOWER_THRESHOLD_HUMIDITY = 0;
    public static final double MAX_UPPER_THRESHOLD_CO2 = 5000;
    public static final double MIN_LOWER_THRESHOLD_CO2 = 0;
}
