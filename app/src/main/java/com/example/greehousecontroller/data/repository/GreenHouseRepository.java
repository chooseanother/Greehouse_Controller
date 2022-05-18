package com.example.greehousecontroller.data.repository;

import static okhttp3.internal.Internal.instance;

import android.app.Application;

import com.example.greehousecontroller.Config;
import com.example.greehousecontroller.data.model.UserLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GreenHouseRepository {

    private final Application app;
    private static GreenHouseRepository instance;
    FirebaseDatabase database;
    DatabaseReference reference;

    private GreenHouseRepository(Application app) {
        this.app = app;
        database = FirebaseDatabase.getInstance(Config.firebase_db);
        reference = database.getReference("users");
    }
    public static synchronized GreenHouseRepository getInstance(Application app) {
        if (instance == null) {
            instance = new GreenHouseRepository(app);
        }
        return instance;
    }
    public boolean getGreenHouseId(){
        return false;
    }
}
