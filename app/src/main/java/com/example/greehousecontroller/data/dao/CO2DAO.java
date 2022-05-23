package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.greehousecontroller.data.model.CO2;

import java.util.List;

@Dao
public interface CO2DAO {
    @Query("SELECT * FROM CO2")
    List<CO2> getAll();

    @Update
    void update(CO2 co2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CO2 co2);
}
