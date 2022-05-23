package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Update;

import com.example.greehousecontroller.data.model.Threshold;

@Dao
public interface ThresholdDAO {
    @Update
    void update(Threshold threshold);
}
