package com.example.greehousecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.greehousecontroller.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
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
                R.id.nav_home, R.id.nav_status, R.id.nav_graphs, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
                // Do some checks or init menu with user info

                // TODO: Check if greenhouse Id exists, else navigate to enter greenhouse id
                viewModel.initUserInfo();

                viewModel.getCurrentUserInfo().observe(this, userInfo -> {
                    Log.i("main-userinfo", userInfo.toString());
                    if (userInfo == null){
                        //Navigate to enter greenhouse id
                    } else if (userInfo.getGreenhouseID() == null){
                        //Navigate to enter greenhouse id
                    } else if (userInfo.getGreenhouseID().equals("")){
                        //Navigate to enter greenhouse id
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

    public void logOut() {
        viewModel.logOut();
    }
}