package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.greehousecontroller.data.model.Temperature;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TemperatureDAO {
    @Query("SELECT * FROM Temperature")
    List<Temperature> getAll();

    @Update
    void update(ArrayList<Temperature> temperatures);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArrayList<Temperature> temperatures);

}
