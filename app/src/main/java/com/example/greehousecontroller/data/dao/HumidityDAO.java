package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.greehousecontroller.data.model.Humidity;

import java.util.List;
@Dao
public interface HumidityDAO {
    @Query("SELECT * FROM Humidity ORDER BY time DESC")
    List<Humidity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Humidity> humidity);

    @Query("DELETE FROM Temperature")
    void delete();
}
