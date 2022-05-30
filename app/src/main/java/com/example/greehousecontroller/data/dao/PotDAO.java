package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.greehousecontroller.data.model.Pot;

import java.util.List;
@Dao
public interface PotDAO {
    @Query("SELECT * FROM Pot")
    List<Pot> getAll();

    @Update
    void update(List<Pot> pot);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Pot> pot);

    @Query("DELETE FROM Pot")
    void delete();
}
