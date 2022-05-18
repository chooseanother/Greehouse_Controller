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
    TextView id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GreenHouseIdActivityViewModel.class);
        checkIfSignedIn();
        setContentView(R.layout.activity_log_in);
        binding = ActivityGreenhouseIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = binding.GreenHouseIDEditText;
        bindLogOutButton();
        bindSaveIdButton();
    }
    private void bindSaveIdButton(){
        binding.SaveGreenHouseID.setOnClickListener(view -> {
            viewModel.saveGreenHouseId(viewModel.getCurrentUser().getValue().getEmail(),id.getText().toString());
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
        LiveData<FirebaseUser> currentUser = viewModel.getCurrentUser();
        currentUser.observe(this, user -> {
            if (user != null){

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

