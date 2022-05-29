package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentAddPotBinding;
import com.example.greehousecontroller.ui.viewmodel.AddPotViewModel;

import java.util.ArrayList;

public class AddPotFragment extends Fragment {

    private AddPotViewModel viewModel;
    private FragmentAddPotBinding binding;
    private NavController navController;
    private String greenhouseId;

    //xml elements
    private EditText potNameEditText;
    private EditText potMinimalHumidityEditText;
    private Button savePotButton;
    private Button cancelPotButton;
    private View root;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(AddPotViewModel.class);
        binding = FragmentAddPotBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragmentContentMain);

        getGreenhouseID();
        setUpBinding();
        setUpSpinner();
        setUpListeners();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getGreenhouseID(){
        viewModel.initUserInfo();
        viewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            greenhouseId = userInfo.getGreenhouseID();
        });
    }

    private void setUpBinding(){
        potNameEditText = binding.addPotNameEditText;
        potMinimalHumidityEditText = binding.addPotMinimalMoistureEditText;
        potMinimalHumidityEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        savePotButton = binding.addPotSaveButton;
        cancelPotButton = binding.addPotCancelButton;
        spinner = binding.addPotSensorSpinner;
    }

    private void setUpListeners(){
        savePotButton.setOnClickListener(view -> {
            boolean result;
            if(spinner.getSelectedItem() == null){
                result = viewModel.validInput(greenhouseId, null , potNameEditText.getText().toString(),
                        potMinimalHumidityEditText.getText().toString());
            }
            else
            {
                result = viewModel.validInput(greenhouseId, spinner.getSelectedItem().toString() , potNameEditText.getText().toString(),
                        potMinimalHumidityEditText.getText().toString());
            }
            if(result){
                viewModel.addPot(greenhouseId, spinner.getSelectedItem().toString(), potNameEditText.getText().toString(), potMinimalHumidityEditText.getText().toString());
                navController.navigate(R.id.navigationHome);
            }
        });

        cancelPotButton.setOnClickListener(view -> navController.navigate(R.id.navigationHome));
    }

    private void setUpSpinner(){
        ArrayList<String> sensors = (ArrayList<String>) viewModel.getAvailableSensors();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), com.anychart.R.layout.support_simple_spinner_dropdown_item, sensors);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}