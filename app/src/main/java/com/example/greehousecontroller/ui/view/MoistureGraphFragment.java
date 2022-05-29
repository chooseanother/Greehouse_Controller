package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.databinding.FragmentMoisturePotsGraphsBinding;
import com.example.greehousecontroller.ui.viewmodel.MoistureViewModel;
import java.util.ArrayList;
import java.util.List;

public class MoistureGraphFragment extends Fragment {
    private FragmentMoisturePotsGraphsBinding binding;
    private MoistureViewModel moistureViewModel;
    private Spinner spinner;
    private List<PotSpinner> spinnerList;
    private NavController navController;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMoisturePotsGraphsBinding.inflate(inflater, container, false);
        moistureViewModel = new ViewModelProvider(this).get(MoistureViewModel.class);
        spinner = binding.spinner;
        initSpinner();
        onChangeSpinner();
        updateMeasurements();
        return binding.getRoot();
    }
    private void onChangeSpinner()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateMeasurements();
                navController = Navigation.findNavController(getActivity(),R.id.moisture_swap_fragment);
                if(navController.getCurrentDestination().getId() == R.id.moisture_swap_fragment) {
                    navController.navigate(R.id.moistureGraph);
                }
                else {
                    navController.navigate(R.id.moistureGraph2);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void initSpinner()
    {
        spinnerList = new ArrayList<>();
        moistureViewModel.getPots().observe(getViewLifecycleOwner(), new Observer<List<Pot>>() {
            @Override
            public void onChanged(@Nullable List<Pot> pots) {
                if (pots.size() > 0) {
                    for (Pot pot : pots) {
                        spinnerList.add(new PotSpinner(pot.getName(), pot.getId()));
                    }
                    ArrayAdapter<PotSpinner> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
                else {
                    spinnerList = new ArrayList<>();
                    ArrayAdapter<PotSpinner> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
            }
        });
    }

    private void updateMeasurements(){
        moistureViewModel.initUserInfo();
        moistureViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            if(spinnerList.size() > 0) {
                moistureViewModel.updateHistoryData(userInfo.getGreenhouseID(), spinnerList.get(spinner.getSelectedItemPosition()).id);
            }
        });
    }

    private static class PotSpinner
    {
        private String name;
        private int id;
        private PotSpinner(String name, int id) {
            this.name = name;
            this.id = id;
        }
        @Override
        public String toString()
        {
            return name;
        }

    }
}
