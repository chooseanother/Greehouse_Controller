package com.example.greehousecontroller.data.dao;

import androidx.room.Query;
import androidx.room.Update;

import com.example.greehousecontroller.data.model.Humidity;

import java.util.List;

public interface HumidityDAO {
    @Query("SELECT * FROM Humidity")
    List<Humidity> getAll();

    @Update
    void update(Humidity humidity);
}
