package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.databinding.FragmentThresholdSettingsBinding;
import com.example.greehousecontroller.ui.viewmodel.ThresholdSettingsViewModel;

import java.text.DecimalFormat;

public class ThresholdSettingsFragment extends Fragment {

    private FragmentThresholdSettingsBinding binding;
    private View root;
    private ThresholdSettingsViewModel viewModel;
    //todo REPLACE TEST BY ACTUAL
    private String greenhouseId;

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
        viewModel = new ViewModelProvider(this).get(ThresholdSettingsViewModel.class);
        getGreenhouseID();
        binding = FragmentThresholdSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        viewModel.loadCachedData();
        setUpBindingThresholdsEditText();
        setUpEditTextNumberOnly();
        setUpBindingThresholdsSave();
        initializeObserve();

        return root;
    }

    private void getGreenhouseID() {
        viewModel.initUserInfo();
        viewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            greenhouseId = userInfo.getGreenhouseID();
        });
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

    private void setUpBindingThresholdsEditText() {
        temperatureLowerThreshold = binding.thresholdSettingsTemperatureLowerThresholdEditText;
        temperatureUpperThreshold = binding.thresholdSettingsTemperatureUpperThresholdEditText;

        co2LowerThreshold = binding.thresholdSettingsCo2LowerThresholdEditText;
        co2UpperThreshold = binding.thresholdSettingsCo2UpperThresholdEditText;

        humidityLowerThreshold = binding.thresholdSettingsHumidityLowerThresholdEditText;
        humidityUpperThreshold = binding.thresholdSettingsHumidityUpperThresholdEditText;
    }

    private void setUpBindingThresholdsSave() {
        //temperature
        saveTemperatureThreshold = binding.thresholdSettingsTemperatureSave;
        saveTemperatureThreshold.setOnClickListener(view -> {
            viewModel.setTemperatureThreshold(greenhouseId, temperatureUpperThreshold.getText().toString(), temperatureLowerThreshold.getText().toString());
        });

        //co2
        saveCo2Threshold = binding.thresholdSettingsCo2Save;
        saveCo2Threshold.setOnClickListener(view -> {
            viewModel.setCo2Threshold(greenhouseId, co2UpperThreshold.getText().toString(), co2UpperThreshold.getText().toString());
        });

        //humidity
        saveHumidityThreshold = binding.thresholdSettingsHumiditySave;
        saveHumidityThreshold.setOnClickListener(view -> {
            viewModel.setHumidityThreshold(greenhouseId, humidityUpperThreshold.getText().toString(), humidityLowerThreshold.getText().toString());
        });
    }

    private void initializeData() {
        viewModel.initializeData(greenhouseId);
    }

    private void initializeObserve() {
        DecimalFormat df = new DecimalFormat("0.0");
        DecimalFormat wholeNumber = new DecimalFormat("0");
        viewModel.getTemperatureThreshold().observe(getViewLifecycleOwner(), threshold -> {
            temperatureLowerThreshold.setText(df.format(threshold.getLowerThreshold()));
            temperatureUpperThreshold.setText(df.format(threshold.getUpperThreshold()));
        });
        viewModel.getHumidityThreshold().observe(getViewLifecycleOwner(), threshold -> {
            humidityLowerThreshold.setText(df.format(threshold.getLowerThreshold()));
            humidityUpperThreshold.setText(df.format(threshold.getUpperThreshold()));
        });
        viewModel.getCo2Threshold().observe(getViewLifecycleOwner(), threshold -> {
            co2LowerThreshold.setText(wholeNumber.format(threshold.getLowerThreshold()));
            co2UpperThreshold.setText(wholeNumber.format(threshold.getUpperThreshold()));
        });
    }

    private void setUpEditTextNumberOnly() {
        temperatureLowerThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        temperatureUpperThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        humidityLowerThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        humidityUpperThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        co2LowerThreshold.setInputType(InputType.TYPE_CLASS_NUMBER);
        co2UpperThreshold.setInputType(InputType.TYPE_CLASS_NUMBER);
    }
}