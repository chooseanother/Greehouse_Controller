package com.example.greehousecontroller.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.greehousecontroller.data.model.UserLiveData;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserRepository {
    private final UserLiveData currentUser;
    private final Application app;
    private static UserRepository instance;

    private UserRepository(Application app) {
        this.app = app;
        currentUser = new UserLiveData();
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
}