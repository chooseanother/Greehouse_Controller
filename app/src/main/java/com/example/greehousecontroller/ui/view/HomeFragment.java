package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.util.Log;
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
import com.example.greehousecontroller.utils.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private PotAdapter adapter;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private String greenhouseId;
    private View root;

    //xml elements
    private TextView temperatureTextView;
    private TextView co2TextView;
    private TextView humidityTextView;
    private TextView welcomingTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        settingOfTextViews();
        getGreenhouseID();
        observeData();
        homeViewModel.getLatestCachedData();
        observeApiStatus();
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
    public void onResume() {
        super.onResume();
        updateLatestMeasurements();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = binding.homeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this::updateLatestMeasurements);
    }

    private void getGreenhouseID() {
        homeViewModel.initUserInfo();
        homeViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            greenhouseId = userInfo.getGreenhouseID();
            if (greenhouseId != null) {
                updateLatestMeasurements();
            }
        });
    }

    private void observeData() {
        homeViewModel.getLatestTemperature().observe(getViewLifecycleOwner(), temperature -> {
            String readings = temperature.getTemperature() + " °C";
            temperatureTextView.setText(readings);
            swipeRefreshLayout.setRefreshing(false);
        });

        homeViewModel.getLatestHumidity().observe(getViewLifecycleOwner(), humidity -> {
            String readings = humidity.getHumidity() + " %";
            humidityTextView.setText(readings);
            swipeRefreshLayout.setRefreshing(false);

        });

        homeViewModel.getLatestCO2().observe(getViewLifecycleOwner(), co2 -> {
            String readings = (int) co2.getCo2Measurement() + " ppm";
            co2TextView.setText(readings);
            swipeRefreshLayout.setRefreshing(false);

        });

    }

    public void settingOfTextViews() {
        // Measurements
        temperatureTextView = binding.homeTemperatureMeasurementTextView;
        co2TextView = binding.homeCo2measurementTextView;
        humidityTextView = binding.homeHumidityMeasurementTextView;

        //Header
        welcomingTextView = binding.homeWelcomingTextView;

        FirebaseUser user = homeViewModel.getUser();
        String welcomeMessage = getString(R.string.home_hello_message);
        if (user != null) {
            welcomeMessage += ", " + homeViewModel.getUser().getDisplayName();
        }
        welcomeMessage += "!";
        welcomingTextView.setText(welcomeMessage);
    }

    private void updateLatestMeasurements() {
        if (greenhouseId != null) {
            homeViewModel.updateLatestMeasurements(greenhouseId);
        }
    }

    private void fabHandle() {
        floatingActionButton = binding.homeFab;
        floatingActionButton.setOnClickListener(clicked -> {
            if (homeViewModel.getLatestPots().getValue().size() < Config.MAX_NUMBER_OF_POTS) {
                ((MainActivity) getActivity()).navController.navigate(R.id.nav_add_pot);
            } else {
                Toast.makeText(getContext(), R.string.home_pot_limit_reached_toast_message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recyclerViewHandle() {
        recyclerView = binding.homeListOfPotsRecycleView;
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PotAdapter(new ArrayList<>());
        homeViewModel.getLatestPots().observe(getViewLifecycleOwner(), pots -> {
            adapter.setPots(pots);
        });

        adapter.setOnClickListener(pot -> {
            Bundle bundle = homeViewModel.getPotBundle(pot);
            ((MainActivity) getActivity()).navController.navigate(R.id.nav_edit_pot, bundle);
        });
    }

    private void observeApiStatus() {
        homeViewModel.getApiFinished().observe(getViewLifecycleOwner(), aBoolean -> {
            Log.d("home", aBoolean.toString());
            swipeRefreshLayout.setRefreshing(aBoolean);
        });
    }
}