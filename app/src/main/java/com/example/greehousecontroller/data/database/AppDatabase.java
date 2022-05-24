package com.example.greehousecontroller.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.greehousecontroller.data.dao.CO2DAO;
import com.example.greehousecontroller.data.dao.HumidityDAO;
import com.example.greehousecontroller.data.dao.PotDAO;
import com.example.greehousecontroller.data.dao.TemperatureDAO;
import com.example.greehousecontroller.data.dao.ThresholdDAO;
import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.data.model.Threshold;

@Database(entities = {CO2.class, Humidity.class, Pot.class, Temperature.class, Threshold.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase instance;

    public abstract CO2DAO co2DAO();
    public abstract HumidityDAO humidityDAO();
    public abstract PotDAO potDAO();
    public abstract TemperatureDAO temperatureDAO();
    public abstract ThresholdDAO thresholdDAO();

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, "database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
