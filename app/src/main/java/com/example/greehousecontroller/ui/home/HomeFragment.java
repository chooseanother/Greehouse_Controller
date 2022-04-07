package com.example.greehousecontroller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.adapters.HomeFragmentAdapter;
import com.example.greehousecontroller.databinding.FragmentHomeBinding;
import com.example.greehousecontroller.model.GreenHouse;
import com.example.greehousecontroller.model.Pot;
import com.example.greehousecontroller.model.User;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private HomeFragmentAdapter adapter;
    private ArrayList<Pot> potArrayList;
    private HomeViewModel homeViewModel;
    private TextView temperatureTextView;
    private TextView co2TextView;
    private TextView humidityTextView;
    private TextView luminosityTextView;
    private TextView welcomingTextView;
    private TextView dayDescriptionTextView;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //View root = binding.getRoot();
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getAllPots().observe(getViewLifecycleOwner(), new Observer<ArrayList<Pot>>() {
            @Override
            public void onChanged(ArrayList<Pot> pots) {
                potArrayList.addAll(pots);
            }
        });
        root = inflater.inflate(R.layout.fragment_home, container, false);
        settingOfTextViews();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        recyclerView = root.findViewById(R.id.listOfPotsRecycleView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        potArrayList = new ArrayList<>();
        adapter = new HomeFragmentAdapter(potArrayList);
        recyclerView.setAdapter(adapter);
        testingData(potArrayList);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void settingOfTextViews(){
        //GreenHouse section
        humidityTextView = root.findViewById(R.id.humidityMeasurementTextView);
        temperatureTextView = root.findViewById(R.id.temperatureMeasurementTextView);
        co2TextView = root.findViewById(R.id.co2measurementTextView);
        luminosityTextView = root.findViewById(R.id.luminosityMeasurementTextView);

        GreenHouse greenHouse = homeViewModel.getGreenHouseData().getValue();
        if(greenHouse != null){
            humidityTextView.setText(greenHouse.getHumidity() + " %");
            temperatureTextView.setText(greenHouse.getTemperature() + " °C");
            co2TextView.setText(greenHouse.getCo2() + " grams");
            luminosityTextView.setText(greenHouse.getLuminosity() + " lum");
        }
        else{
            humidityTextView.setText("Unkown" + " %");
            temperatureTextView.setText("Unknown" + " °C");
            co2TextView.setText("Unknown" + " grams");
            luminosityTextView.setText("Unknown" + " lum");
        }
        //Header
        welcomingTextView = root.findViewById(R.id.welcomingTextView);
        dayDescriptionTextView = root.findViewById(R.id.dayDescriptionTextView);
        User user = homeViewModel.getUser().getValue();
        if(user != null){
            welcomingTextView.setText("Hello, " + homeViewModel.getUser().getValue().getName() + "!");
        }
        else{
            welcomingTextView.setText("Hello!");
        }
        //For now
        dayDescriptionTextView.setText("It's a sunny day!");
    }

    public void testingData(ArrayList<Pot> pots){
        pots.add(new Pot(1, 60));
        pots.add(new Pot(2, 59));
        pots.add(new Pot(3, 0));
    }
}