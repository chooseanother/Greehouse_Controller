package com.example.greehousecontroller.data.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserLiveData extends LiveData<FirebaseUser> {
    private final FirebaseAuth.AuthStateListener listener = firebaseAuth -> setValue(firebaseAuth.getCurrentUser());

    @Override
    protected void onActive() {
        super.onActive();
        FirebaseAuth.getInstance().addAuthStateListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        FirebaseAuth.getInstance().removeAuthStateListener(listener);
    }
}
