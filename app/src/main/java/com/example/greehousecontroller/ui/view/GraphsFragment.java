package com.example.greehousecontroller.ui.view;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anychart.chart.common.dataentry.HighLowDataEntry;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentGraphsBinding;
import com.example.greehousecontroller.ui.viewmodel.GraphsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GraphsFragment extends Fragment {
    private FragmentGraphsBinding binding;
    private GraphsViewModel graphsViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    public String greenhouseid;

    private BottomNavigationView bottomAppBar;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);
        binding = FragmentGraphsBinding.inflate(inflater, container, false);
        bottomAppBar = binding.graphsBottomNavigation;
        updateMeasurements();
        initSwipeRefreshLayout();
        observeData();
        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(),R.id.graphsNavHostFragment);
        graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);
        bottomAppBar = binding.graphsBottomNavigation;
        bottomAppBar.setOnNavigationItemSelectedListener( item -> {
            switch (item.getItemId()) {
                case R.id.graphsNavigationTemperature:
                        navController.navigate(R.id.graphsNavigationTemperatureGraph);
                    return true;
                case R.id.graphsNavigationHumidity:
                        navController.navigate(R.id.graphsNavigationHumidityGraph);
                    return true;
                case R.id.graphsNavigationCo2:
                        navController.navigate(R.id.graphsNavigationCo2Graph);
                    return true;
                case R.id.graphsNavigationMoisture:
                    navController.navigate(R.id.graphsNavigationMoistureGraph);
                    return true;
            }
            return false;
        });
    }

    private void initSwipeRefreshLayout(){
        swipeRefreshLayout = binding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this::updateLatestMeasurements);
    }
    private void updateMeasurements(){

        graphsViewModel.initUserInfo();
        graphsViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            greenhouseid = userInfo.getGreenhouseID();
        });
    }

    private void updateLatestMeasurements(){
        // TODO: Figure out how to handle greenhouseId
        if(greenhouseid != null) {
            graphsViewModel.updateHistoryData(greenhouseid);
        }
    }

    private void observeData(){
        graphsViewModel.getTemperatureHistoryData().observe(getViewLifecycleOwner(),temperature -> {
            swipeRefreshLayout.setRefreshing(false);
        });
    }


    static class OHCLDataEntry extends HighLowDataEntry {
        OHCLDataEntry(Long x, Double open, Double high, Double low, Double close) {
            super(x, high, low);
            setValue("open", open);
            setValue("close", close);
        }
    }
}