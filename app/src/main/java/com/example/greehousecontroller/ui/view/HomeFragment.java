package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.greehousecontroller.MainActivity;
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentHomeBinding;
import com.example.greehousecontroller.data.model.GreenHouse;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.model.User;
import com.example.greehousecontroller.ui.adapter.PotAdapter;
import com.example.greehousecontroller.ui.viewmodel.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PotAdapter adapter;
    private ArrayList<Pot> potArrayList;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private TextView temperatureTextView;
    private TextView co2TextView;
    private TextView humidityTextView;
    private TextView welcomingTextView;
    private TextView dayDescriptionTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View root;
    private FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getAllPots().observe(getViewLifecycleOwner(), pots -> potArrayList.addAll(pots));

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        settingOfTextViews();
        recyclerView = root.findViewById(R.id.listOfPotsRecycleView);
        floatingActionButton = root.findViewById(R.id.fab);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        potArrayList = new ArrayList<>();
        testingData(potArrayList);
        adapter = new PotAdapter(potArrayList);
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(clicked->{
            ((MainActivity)getActivity()).navController.navigate(R.id.nav_add_pot);
        });
        adapter.setOnClickListener(pot -> {
            Fragment fragment = new Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(pot.getId()));
            fragment.setArguments(bundle);
            ((MainActivity)getActivity()).navController.navigate(R.id.nav_edit_pot, bundle);
        });
        observeData();
        initSwipeRefreshLayout();
        return root;
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
    public void callParentMethod(){
        getActivity().onBackPressed();
    }

    private void initSwipeRefreshLayout(){
        swipeRefreshLayout = root.findViewById(R.id.homeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            updateLatestMeasurements();

            // TODO: figure out where to place this so it stops when it gets data from API
            //  maybe with callback, or inside observe data
            // swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void observeData(){
        homeViewModel.getLatestTemperature().observe(getViewLifecycleOwner(),temperature -> {
            String readings = temperature.getTemperature() + " °C";
            temperatureTextView.setText(readings);

            // TODO: Maybe here?
            swipeRefreshLayout.setRefreshing(false);

            // TODO: Remove this when testing is done
            String show = "Date: "+temperature.getTime()+" T: "+temperature.getTemperature()+" °C";
            Toast.makeText(getContext(), show, Toast.LENGTH_SHORT).show();

        });
        homeViewModel.getLatestHumidity().observe(getViewLifecycleOwner(),humidity -> {
            String readings = humidity.getHumidity() + " %";
            humidityTextView.setText(readings);
        });

    }

    public void settingOfTextViews(){
        //GreenHouse section
        humidityTextView = root.findViewById(R.id.humidityMeasurementTextView);
        temperatureTextView = root.findViewById(R.id.temperatureMeasurementTextView);
        co2TextView = root.findViewById(R.id.co2measurementTextView);

        GreenHouse greenHouse = homeViewModel.getGreenHouseData().getValue();
        if(greenHouse != null){
//            humidityTextView.setText(greenHouse.getHumidity() + " %");
//            temperatureTextView.setText(greenHouse.getTemperature() + " °C");
            co2TextView.setText(greenHouse.getCo2() + " grams");
        }
        else{
//            humidityTextView.setText("Unkown" + " %");
//            temperatureTextView.setText("Unknown" + " °C");
            co2TextView.setText("Unknown" + " grams");
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
        pots.add(new Pot(1, "Cactus", 60, 20));
        pots.add(new Pot(2, "Flower", 59, 50));
        pots.add(new Pot(3, "Tomato", 0, 0));
        pots.add(new Pot(4,"Potato", 0, 0));
        pots.add(new Pot(5, "Weed", 0, 0));
        pots.add(new Pot(6, "More weed", 0, 0));
        pots.add(new Pot(7, "More more weed", 0, 0));
        pots.add(new Pot(8, "More more more weed", 0, 0));
    }

    private void updateLatestMeasurements(){
        // TODO: Figure out how to handle greenhouseId
        homeViewModel.updateLatestMeasurements("test");
    }
}