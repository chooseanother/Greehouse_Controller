package com.example.greehousecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.databinding.ActivityGreenhouseIdBinding;
import com.example.greehousecontroller.databinding.ActivityLogInBinding;
import com.example.greehousecontroller.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseUser;

public class GreenHouseIdActivity extends AppCompatActivity {
    private ActivityGreenhouseIdBinding binding;
    private GreenHouseIdActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GreenHouseIdActivityViewModel.class);
        checkIfSignedIn();
        binding = ActivityGreenhouseIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupBindings();
    }

    private void setupBindings(){
        bindLogOutButton();
        bindSaveIdButton();
    }

    private void bindSaveIdButton(){
        binding.SaveGreenHouseID.setOnClickListener(view -> {
            viewModel.saveGreenHouseId(binding.GreenHouseIDEditText.getText().toString());
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void bindLogOutButton(){
        binding.signOutGreenhouseid.setOnClickListener(view -> {
            logOut();
        });
    }

    public void logOut() {
        viewModel.logOut();
    }

    private void checkIfSignedIn(){
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null){
                viewModel.initUserInfo();
            } else {
                startLoginActivity();
            }
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LogInActivity.class));
        finish();
    }

}

