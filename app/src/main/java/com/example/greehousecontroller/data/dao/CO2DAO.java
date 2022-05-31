package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.greehousecontroller.data.model.CO2;

import java.util.List;

@Dao
public interface CO2DAO {
    @Query("SELECT * FROM CO2")
    List<CO2> getAll();

    @Query("SELECT * FROM CO2 WHERE time = (SELECT MAX(time) FROM CO2)")
    CO2 getLatest();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CO2> co2s);

    @Query("DELETE FROM Temperature")
    void delete();
}
