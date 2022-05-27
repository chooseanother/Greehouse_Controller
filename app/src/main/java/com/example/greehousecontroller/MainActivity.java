package com.example.greehousecontroller;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.greehousecontroller.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public NavController navController;
    private MainActivityViewModel viewModel;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();
        getCurrentToken();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        setupNavigation();
        bindLogOutMenu();
        toolbar = binding.appBarMain.toolbar;

    }

    private void initViews() {
        setSupportActionBar(binding.appBarMain.toolbar);
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_status, R.id.nav_graphs, R.id.nav_settings, R.id.nav_edit_id)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void checkIfSignedIn(){
        LiveData<FirebaseUser> currentUser = viewModel.getCurrentUser();
        currentUser.observe(this, user -> {
            if (user != null){
                // Init menu with user info
                setUpMenuUserInfo();

                viewModel.initUserInfo();

                viewModel.getCurrentUserInfo().observe(this, userInfo -> {
                    // If for some reason user info is deleted from database when application is
                    //  running, then this will prevent a crash.
                    if (userInfo == null){
                        startGreenhouseActivity();
                        finish();
                    }

                    if (userInfo.getGreenhouseID() == null) {
                        startGreenhouseActivity();
                        finish();
                    } else if (userInfo.getGreenhouseID().equals("")) {
                        startGreenhouseActivity();
                        finish();
                    }

                });
            } else {
                startLoginActivity();
            }
        });
    }


    private void bindLogOutMenu(){
        binding.menuLogOut.setOnClickListener(view -> {
            logOut();
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LogInActivity.class));
        finish();
    }

    private void startGreenhouseActivity(){
        startActivity(new Intent(this, GreenHouseIdActivity.class));
        finish();
    }

    public void logOut() {
        viewModel.logOut();
    }

    private void setUpMenuUserInfo(){
        FirebaseUser user = viewModel.getCurrentUser().getValue();
        TextView username = binding.navView.getHeaderView(0).findViewById(R.id.menu_user_name);
        username.setText(user.getDisplayName());
        TextView email = binding.navView.getHeaderView(0).findViewById(R.id.menu_user_email);
        email.setText(user.getEmail());
        ImageView image = binding.navView.getHeaderView(0).findViewById(R.id.menu_user_image);
        Uri photoUrl = user.getPhotoUrl();
        Glide.with(MainActivity.this).load(photoUrl).into(image);
    }

    // When you need to retrieve the current token
    // For sending test notifications to a specific device, we aren't using it anywhere else
    // when done doing development and testing this should be removed
    public void getCurrentToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("FCM-getCurrentToken", "Fetching FCM registration token failed", task.getException());
                return;
            }

            // Get new FCM registration token
            String token = task.getResult();

            // Log and toast
            String msg = "FCM token: "+token;
            Log.d("FCM-getCurrentToken", msg);
            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        });
    }
}