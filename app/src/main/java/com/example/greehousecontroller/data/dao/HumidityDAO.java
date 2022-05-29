package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.greehousecontroller.data.model.Humidity;

import java.util.List;
@Dao
public interface HumidityDAO {
    @Query("SELECT * FROM Humidity")
    List<Humidity> getAll();

    @Query("SELECT * FROM Humidity WHERE time = (SELECT MAX(time) FROM Humidity)")
    Humidity getLatest();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Humidity> humidity);

    @Query("DELETE FROM Temperature")
    void delete();
}
