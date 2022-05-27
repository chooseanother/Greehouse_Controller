package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greehousecontroller.databinding.FragmentStatusBinding;
import com.example.greehousecontroller.ui.adapter.SensorAdapter;
import com.example.greehousecontroller.ui.viewmodel.StatusViewModel;

import java.util.ArrayList;

public class StatusFragment extends Fragment {

    private RecyclerView recyclerView;
    private SensorAdapter adapter;
    private StatusViewModel statusViewModel;
    private FragmentStatusBinding binding;
    private String greenhouseId;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statusViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        binding = FragmentStatusBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        getGreenhouseId();
        updateSensorStatus();
        recyclerViewHandle();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSensorStatus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getGreenhouseId(){
        statusViewModel.initUserInfo();
        statusViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            greenhouseId = userInfo.getGreenhouseID();
            if(greenhouseId != null) {
                updateSensorStatus();
            }
        });
    }

    private void recyclerViewHandle(){
        recyclerView = binding.statusListOfSensorsRecyclerView;
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SensorAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        statusViewModel.getSensorsStatus().observe(getViewLifecycleOwner(), sensors -> {
            adapter.setSensors(sensors);
            Log.i("Fra-stat",sensors.toString());
        });
    }

    private void updateSensorStatus(){
        if(greenhouseId != null){
            statusViewModel.updateSensorStatus(greenhouseId);
        }
    }
}