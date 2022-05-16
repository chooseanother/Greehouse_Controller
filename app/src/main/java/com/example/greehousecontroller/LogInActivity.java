package com.example.greehousecontroller;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.greehousecontroller.databinding.ActivityLogInBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;

import java.util.Arrays;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    private ActivityLogInBinding binding;
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
            if (response == null){
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                signIn();
            } else {
                Log.e("Login", response.getError().getMessage());
            }
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(LogInActivity.this, MainActivity.class));
        finish();
    }

    private void goToEnterGreenhouseCode() {
        //startActivity(new Intent(LogInActivity.this, EnterGreenhouseCode.class));
        finish();
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null){
                // handle result
                handleResult(user.getDisplayName());
            } else {
                signIn();
            }
        });
    }

    private void handleResult(String name){
        // get user info from firebase to check if they have greenhouseId/Code registered
        goToMainActivity();

        // might need this later
        /*viewModel.initUserInfo();

        viewModel.getCurrentUserInfo().observe(this, userInfo -> {
            if (userInfo == null){
                //String name = currentUser.getValue().getDisplayName();
                viewModel.saveUserInfo(name, "", "");

            }
            else {
                String householdId = userInfo.getHouseholdId();
                Log.d("signIn", "Household " + householdId);
                if (householdId == null) {

                } else if (householdId.equals("")) {
                    goToEnterGreenhouseCode();
                } else {
                    // navigate to mainActivity
                    goToMainActivity();
                }
            }
        });*/
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