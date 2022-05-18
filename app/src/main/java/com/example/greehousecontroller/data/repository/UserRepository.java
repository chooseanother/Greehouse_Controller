package com.example.greehousecontroller.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.Config;
import com.example.greehousecontroller.data.model.UserLiveData;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserRepository {
    private final UserLiveData currentUser;
    private final Application app;
    private static UserRepository instance;
    FirebaseDatabase database;
    DatabaseReference reference;

    private UserRepository(Application app) {
        this.app = app;
        currentUser = new UserLiveData();
        database = FirebaseDatabase.getInstance(Config.firebase_db);
        reference = database.getReference("users");
    }
    public static synchronized UserRepository getInstance(Application app) {
        if (instance == null) {
            instance = new UserRepository(app);
        }
        return instance;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    public void signOut() {
        AuthUI.getInstance().signOut(app.getApplicationContext());
    }
    public void saveGreenHouseId(String email,String id)
    {
        reference.child(getSafeEmail(email)).child("GreenHouseID").setValue(id);
    }
    public String getSafeEmail(String email)
    {
        return email.replace('.','-');
    }

}