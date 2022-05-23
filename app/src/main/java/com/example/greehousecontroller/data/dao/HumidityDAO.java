package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.greehousecontroller.data.model.Humidity;

import java.util.List;
@Dao
public interface HumidityDAO {
    @Query("SELECT * FROM Humidity")
    List<Humidity> getAll();

    @Update
    void update(Humidity humidity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Humidity humidity);
}
