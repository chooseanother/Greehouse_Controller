package com.example.greehousecontroller.data.dao;

import androidx.room.Query;
import androidx.room.Update;

import com.example.greehousecontroller.data.model.Temperature;

import java.util.ArrayList;

public interface TemperatureDAO {
    @Query("SELECT * FROM Temperature")
    ArrayList<Temperature> getAll();

    @Update
    void update(ArrayList<Temperature> temperatures);
}
