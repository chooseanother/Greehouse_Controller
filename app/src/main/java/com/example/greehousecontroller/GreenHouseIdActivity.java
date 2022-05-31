package com.example.greehousecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.databinding.ActivityGreenhouseIdBinding;

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

    private void setupBindings() {
        bindLogOutButton();
        bindSaveIdButton();
        greenhouseIDText = binding.enterGreenhouseIdEditText;
    }

    private void bindSaveIdButton() {
        binding.enterGreenhouseIdSaveButton.setOnClickListener(view -> {
            String greenhouseID = greenhouseIDText.getText().toString();
            viewModel.saveGreenHouseId(greenhouseID);
            viewModel.subscribeToGreenhouse(greenhouseID);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void bindLogOutButton() {
        binding.enterGreenhouseIdSignOutButton.setOnClickListener(view -> {
            logOut();
        });
    }

    public void logOut() {
        viewModel.logOut();
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
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

