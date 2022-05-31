package com.example.greehousecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.databinding.ActivityLogInBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;

import java.util.Arrays;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    private ActivityLogInBinding binding;    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );
    private LogInActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(LogInActivityViewModel.class);

        checkIfSignedIn();
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            handleResult(viewModel.getCurrentUser().getValue().getDisplayName());
        } else {
            if (response == null) {
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                signIn();
            } else {
                Log.e("Login", response.getError().getMessage());
            }
        }
    }

    private void startMainActivity() {
        startActivity(new Intent(LogInActivity.this, MainActivity.class));
        finish();
    }

    private void startGreenhouseActivity() {
        startActivity(new Intent(LogInActivity.this, GreenHouseIdActivity.class));
        finish();
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                // handle result
                handleResult(user.getDisplayName());
            } else {
                signIn();
            }
        });
    }

    private void handleResult(String name) {
        // get user info from firebase to check if they have greenhouseId/Code registered
        viewModel.initUserInfo();

        viewModel.getCurrentUserInfo().observe(this, userInfo -> {
            if (userInfo == null) {
                startGreenhouseActivity();
            } else {
                startMainActivity();
            }
        });
    }

    private void signIn() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        activityResultLauncher.launch(signInIntent);
    }


}