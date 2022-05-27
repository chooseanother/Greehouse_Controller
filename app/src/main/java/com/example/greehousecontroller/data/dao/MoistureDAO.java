package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.greehousecontroller.data.model.Moisture;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MoistureDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(ArrayList<Moisture> moisture);

    @Query("DELETE FROM Moisture WHERE moisture.potId = :potId")
    void delete(int potId);

    @Query("SELECT * FROM Moisture WHERE moisture.potId = :potId ORDER BY time DESC")
    List<Moisture> getAllByPotId(int potId);
}
