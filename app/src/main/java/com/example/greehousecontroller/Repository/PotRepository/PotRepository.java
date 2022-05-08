package com.example.greehousecontroller.Repository.PotRepository;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PotRepository {
    private PotLiveData currentPot;
    private static PotRepository instance;
    FirebaseDatabase database;
    DatabaseReference reference;

    private PotRepository(){
    }

    public static synchronized PotRepository getInstance(){
        if(instance == null){
            instance = new PotRepository();
        }
        return  instance;
    }

    public void init(String name){
        //accessing DB to get current pot details
    }

    public PotLiveData getCurrentPot() {
        return currentPot;
    }

    public void deleteCurrentPot() {
        //accessing DB to delete current pot
    }

    public boolean updateCurrentPot(String name, String minimumThreshold) {
        return false;
    }
}
