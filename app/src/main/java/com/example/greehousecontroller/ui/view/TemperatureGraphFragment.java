package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Stock;
import com.anychart.core.stock.Plot;
import com.anychart.data.Table;
import com.anychart.enums.StockSeriesType;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.databinding.TemperatureGraphBinding;
import com.example.greehousecontroller.ui.viewmodel.TemperatureGraphViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemperatureGraphFragment extends Fragment {

    private TemperatureGraphBinding binding;
    private AnyChartView temperatureChart;
    private ProgressBar progressBar;
    private TemperatureGraphViewModel temperatureGraphViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TemperatureGraphBinding.inflate(inflater, container, false);
        temperatureGraphViewModel = new ViewModelProvider(this).get(TemperatureGraphViewModel.class);
        setBindings();
        temperatureGraphViewModel.loadCachedData();
        updateMeasurements();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setBindings() {
        temperatureChart = binding.temperatureChart;
        progressBar = binding.temperatureProgressBar;
    }

    public Table temperatureGraph() {
        Table table = Table.instantiate("x");
        List<DataEntry> data = new ArrayList<>();
        temperatureGraphViewModel.getTemperatureHistoryData().observe(getViewLifecycleOwner(), new Observer<List<Temperature>>() {
            @Override
            public void onChanged(List<Temperature> temperatures) {
                if (temperatures != null) {
                    if (temperatures.size() > 0) {
                        for (int i = 0; i < 1; i++) {
                            long localTime = (long) Objects.requireNonNull(temperatureGraphViewModel.getLatestTemperature().getValue()).getLocalTime();
                            double measurement = temperatureGraphViewModel.getLatestTemperature().getValue().getTemperature();
                            data.add(new GraphsFragment.OHCLDataEntry(localTime, 0.1, 0.1, 0.1, measurement));
                        }
                        for (Temperature temperature : temperatures) {
                            data.add(new GraphsFragment.OHCLDataEntry(temperature.getLocalTime(), temperature.getTemperature(), temperature.getTemperature(), temperature.getTemperature(), temperature.getTemperature()));
                        }
                        table.addData(data);
                    }
                }
            }
        });
        return table;
    }

    public void initTemperatureChart() {
        if (temperatureGraphViewModel.getUserInfo().getValue() != null) {
            if (temperatureGraphViewModel.getLatestTemperature() != null) {
                temperatureChart.setProgressBar(progressBar);
                APIlib.getInstance().setActiveAnyChartView(temperatureChart);
                Stock stock4 = AnyChart.stock();
                Plot plot4 = stock4.plot(0);
                plot4.ema(temperatureGraph().mapAs("{value: 'close'}"), 1d, StockSeriesType.LINE);
                plot4.yAxis(0).labels().format("{%Value} ??C");
                stock4.title().text("Temperature");
                stock4.title().enabled(true);
                temperatureChart.setChart(stock4);
            }
        }
    }

    private void updateMeasurements() {
        temperatureGraphViewModel.initUserInfo();
        temperatureGraphViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            temperatureGraphViewModel.updateHistoryData(userInfo.getGreenhouseID());
            if (userInfo.getGreenhouseID() != null) {
                initTemperatureChart();
            }
        });
    }
}
