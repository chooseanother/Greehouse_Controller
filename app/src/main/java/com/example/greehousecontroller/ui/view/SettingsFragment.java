package com.example.greehousecontroller.ui.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Threshold;
import com.example.greehousecontroller.databinding.FragmentSettingsBinding;
import com.example.greehousecontroller.ui.viewmodel.SettingsViewModel;

import java.text.DecimalFormat;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private View root;
    private SettingsViewModel viewModel;
    //todo REPLACE TEST BY ACTUAL
    private String greenhouseId = "test";

    //VIEW
    //edit text for thresholds
    private EditText temperatureUpperThreshold;
    private EditText temperatureLowerThreshold;
    private EditText co2UpperThreshold;
    private EditText co2LowerThreshold;
    private EditText humidityUpperThreshold;
    private EditText humidityLowerThreshold;
    //save thresholds
    private ImageView saveTemperatureThreshold;
    private ImageView saveCo2Threshold;
    private ImageView saveHumidityThreshold;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        setUpBindingThresholdsEditText();
        setUpBindingThresholdsSave();
        initializeObserve();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpBindingThresholdsEditText(){
        temperatureLowerThreshold = binding.settingsTemperatureLowerTEditText;
        temperatureUpperThreshold = binding.settingsTemperatureUpperTEditText;

        co2LowerThreshold = binding.settingsCo2LowerTEditText;
        co2UpperThreshold = binding.settingsCo2UpperTEditText;

        humidityLowerThreshold = binding.settingsHumidityLowerTEditText;
        humidityUpperThreshold = binding.settingsHumidityUpperTEditText;
    }

    private void setUpBindingThresholdsSave(){
        //temperature
        saveTemperatureThreshold = binding.settingsTemperatureSave;
        saveTemperatureThreshold.setOnClickListener(view -> {
            //viewmodel checks if the entered value is double
            if(!viewModel.setTemperatureThreshold(greenhouseId, temperatureUpperThreshold.getText().toString(), temperatureLowerThreshold.getText().toString())){
                //it is not double
                Toast.makeText(getContext(), R.string.settings_wrong_number_format_temperature_exception, Toast.LENGTH_SHORT).show();
            }
            else{
                //it is double, changes saved
                Toast.makeText(getContext(), R.string.settings_changes_saved, Toast.LENGTH_SHORT).show();
            }
        });

        //co2
        saveCo2Threshold = binding.settingsCo2Save;
        saveCo2Threshold.setOnClickListener(view -> {
            //viewmodel checks if the entered value is double
            if(!viewModel.setCo2Threshold(greenhouseId, co2UpperThreshold.getText().toString(), co2UpperThreshold.getText().toString())){
                //it is not double
                Toast.makeText(getContext(), R.string.settings_wrong_number_format_co2_exception, Toast.LENGTH_SHORT).show();
            }
            else{
                //it is double, changes saved
                Toast.makeText(getContext(), R.string.settings_changes_saved, Toast.LENGTH_SHORT).show();
            }
        });

        //humidity
        saveHumidityThreshold = binding.settingsHumiditySave;
        saveHumidityThreshold.setOnClickListener(view -> {
            //viewmodel checks if the entered value is double
            if(!viewModel.setHumidityThreshold(greenhouseId, humidityUpperThreshold.getText().toString(), humidityLowerThreshold.getText().toString())){
                //it is not double
                Toast.makeText(getContext(), R.string.settings_wrong_number_format_humidity_exception, Toast.LENGTH_SHORT).show();
            }
            else{
                //it is double, changes saved
                Toast.makeText(getContext(), R.string.settings_changes_saved, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeData(){
        viewModel.initializeData(greenhouseId);

    }

    private void initializeObserve(){
        DecimalFormat df = new DecimalFormat("0.0");
        DecimalFormat wholeNumber = new DecimalFormat("0");
        viewModel.getTemperatureThreshold().observe(getViewLifecycleOwner(),threshold -> {
            temperatureLowerThreshold.setText(df.format(threshold.getLowerThreshold()));
            temperatureUpperThreshold.setText(df.format(threshold.getUpperThreshold()));
        });
        viewModel.getHumidityThreshold().observe(getViewLifecycleOwner(),threshold -> {
            humidityLowerThreshold.setText(df.format(threshold.getLowerThreshold()));
            humidityUpperThreshold.setText(df.format(threshold.getUpperThreshold()));
        });
        viewModel.getCo2Threshold().observe(getViewLifecycleOwner(),threshold -> {
            co2LowerThreshold.setText(wholeNumber.format(threshold.getLowerThreshold()));
            co2UpperThreshold.setText(wholeNumber.format(threshold.getUpperThreshold()));
        });
    }

    private void setUpEditTextNumberOnly(){
        temperatureLowerThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        temperatureUpperThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        humidityLowerThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        humidityUpperThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        co2LowerThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        co2UpperThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }
}