package com.example.greehousecontroller.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UserInfoLiveData extends LiveData<UserInfo> {
    private DatabaseReference databaseReference;

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            UserInfo userInfo = snapshot.getValue(UserInfo.class);
            setValue(userInfo);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public UserInfoLiveData(DatabaseReference ref){
        databaseReference = ref;
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.addValueEventListener(listener);
    }
}
