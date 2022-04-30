package com.example.greehousecontroller.Repository.UserRepository;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;


public class UserRepository {
    private UserLiveData currentUser;
    private static UserRepository instance;
    FirebaseDatabase database;
    DatabaseReference reference;

    private UserRepository() {
    }
    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }
    public void init() {
        //reference =getUserDbReference(FirebaseDatabase.getInstance("").getReference("users"));
        //userItemModel = new UserLiveData(reference);
    }



    public UserLiveData getCurrentUser() {
        return currentUser;
    }

    public void signOut(Application app) {
        AuthUI.getInstance()
                .signOut(app.getApplicationContext());
    }

    @NonNull
    public String getSafeCurrentUserEmail(String email) {
        return email.replace('.', '-');
    }

}