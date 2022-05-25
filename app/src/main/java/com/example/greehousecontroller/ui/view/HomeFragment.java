package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.greehousecontroller.MainActivity;
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentHomeBinding;
import com.example.greehousecontroller.ui.adapter.PotAdapter;
import com.example.greehousecontroller.ui.viewmodel.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PotAdapter adapter;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private TextView temperatureTextView;
    private TextView co2TextView;
    private TextView humidityTextView;
    private TextView welcomingTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View root;
    private FloatingActionButton floatingActionButton;
    private String greenhouseid;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        settingOfTextViews();
        getGreenhouseID();
        observeData();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabHandle();
        recyclerViewHandle();
        initSwipeRefreshLayout();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        // TODO: Figure out where to place this, is this the best place
        //  since this is always called no matter what
        updateLatestMeasurements();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initSwipeRefreshLayout(){
        swipeRefreshLayout = binding.homeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this::updateLatestMeasurements);
    }

    private void getGreenhouseID(){
        homeViewModel.initUserInfo();
        homeViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            greenhouseid = userInfo.getGreenhouseID();
            if(greenhouseid != null) {
                updateLatestMeasurements();
            }
        });
    }

    private void observeData(){
        homeViewModel.getLatestTemperature().observe(getViewLifecycleOwner(),temperature -> {
            String readings = temperature.getTemperature() + " °C";
            temperatureTextView.setText(readings);

            // TODO: Maybe here?
            swipeRefreshLayout.setRefreshing(false);

            // TODO: Remove this when testing is done
            Date date = new Date(temperature.getTime()*1000);
            String show = "Date: "+date+" T: "+temperature.getTemperature()+" °C";
            Toast.makeText(getContext(), show, Toast.LENGTH_SHORT).show();

        });

        homeViewModel.getLatestHumidity().observe(getViewLifecycleOwner(),humidity -> {
            String readings = humidity.getHumidity() + " %";
            humidityTextView.setText(readings);
        });

        homeViewModel.getLatestCO2().observe(getViewLifecycleOwner(), co2 -> {
            String readings = (int)co2.getCo2Measurement() + " ppm";
            co2TextView.setText(readings);
        });

    }

    public void settingOfTextViews(){
        // Measurements
        temperatureTextView = binding.temperatureMeasurementTextView;
        co2TextView = binding.co2measurementTextView;
        humidityTextView = binding.humidityMeasurementTextView;

        //Header
        welcomingTextView = binding.welcomingTextView;

        FirebaseUser user = homeViewModel.getUser();
        if(user != null){
            String welcomeMessage = "Hello, " + homeViewModel.getUser().getDisplayName() + "!";
            welcomingTextView.setText(welcomeMessage);
        }
        else{
            welcomingTextView.setText("Hello!");
        }
    }

    private void updateLatestMeasurements(){
        // TODO: Figure out how to handle greenhouseId
        if(greenhouseid != null) {
            homeViewModel.updateLatestMeasurements(greenhouseid);
        }
    }

    private void fabHandle(){
        floatingActionButton = binding.fab;
        if(homeViewModel.getLatestPots().getValue().size() < 6){
            floatingActionButton.setOnClickListener(clicked->{
                ((MainActivity)getActivity()).navController.navigate(R.id.nav_add_pot);
            });
        }
    }

    private void recyclerViewHandle(){
        recyclerView = binding.listOfPotsRecycleView;
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PotAdapter(new ArrayList<>());
        homeViewModel.getLatestPots().observe(getViewLifecycleOwner(), pots -> {
            adapter.setPots(pots);
        });

        adapter.setOnClickListener(pot -> {
            Bundle bundle = homeViewModel.getPotBundle(pot);
            ((MainActivity)getActivity()).navController.navigate(R.id.nav_edit_pot, bundle);
        });
    }
}