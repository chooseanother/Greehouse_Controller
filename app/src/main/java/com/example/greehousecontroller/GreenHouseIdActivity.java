package com.example.greehousecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.databinding.ActivityGreenhouseIdBinding;
import com.example.greehousecontroller.databinding.ActivityLogInBinding;
import com.example.greehousecontroller.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class GreenHouseIdActivity extends AppCompatActivity {
    private ActivityGreenhouseIdBinding binding;
    private GreenHouseIdActivityViewModel viewModel;
    private EditText greenhouseIDText;

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
        greenhouseIDText = binding.enterGreenhouseIdEditText;
    }

    private void bindSaveIdButton(){
        binding.enterGreenhouseIdSaveButton.setOnClickListener(view -> {
            String greenhouseID = greenhouseIDText.getText().toString();
            viewModel.saveGreenHouseId(greenhouseID);
            viewModel.subscribeToGreenhouse(greenhouseID);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void bindLogOutButton(){
        binding.enterGreenhouseIdSignOutButton.setOnClickListener(view -> {
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

