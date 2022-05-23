package com.example.greehousecontroller.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.greehousecontroller.data.model.Threshold;

@Dao
public interface ThresholdDAO {
    @Query("UPDATE Threshold SET upperThreshold = :upperThreshold AND lowerThreshold = :lowerThreshold  WHERE type= :type")
    void update(String type, double upperThreshold, double lowerThreshold);

    @Query("SELECT * FROM Threshold  WHERE type= :type")
    Threshold getThreshold(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Threshold threshold);
}
