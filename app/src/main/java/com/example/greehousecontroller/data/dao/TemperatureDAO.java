package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Temperature;

import java.util.List;

@Dao
public interface TemperatureDAO {
    @Query("SELECT * FROM Temperature")
    List<Temperature> getAll();

    @Query("SELECT * FROM Temperature WHERE time = (SELECT MAX(time) FROM Temperature)")
    Temperature getLatest();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Temperature> temperatures);

    @Query("DELETE FROM Temperature")
    void delete();
}
