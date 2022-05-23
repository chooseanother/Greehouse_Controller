package com.example.greehousecontroller.data.dao;

import androidx.room.Query;
import androidx.room.Update;

import com.example.greehousecontroller.data.model.Pot;

import java.util.List;

public interface PotDAO {
    @Query("SELECT * FROM Pot")
    List<Pot> getAll();

    @Update
    void update(List<Pot> pot);
}
