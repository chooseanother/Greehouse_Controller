package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    AnyChartView temperatureChart;
    private TemperatureGraphViewModel temperatureGraphViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TemperatureGraphBinding.inflate(inflater, container, false);
        temperatureChart = binding.temperatureChart;
        temperatureGraphViewModel = new ViewModelProvider(this).get(TemperatureGraphViewModel.class);
        updateMeasurements();
        initTemperatureChart();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateMeasurements();
        initTemperatureChart();
    }

    public Table temperatureGraph()
    {
        Table table = Table.instantiate("x");
        List<DataEntry> data = new ArrayList<>();
        temperatureGraphViewModel.getTemperatureHistoryData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Temperature>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Temperature> temperatures) {
                if (temperatures.size() > 0) {
                    for (int i = 0; i < 1; i++) {
                        System.out.println("===========================");
                        data.add(new GraphsFragment.OHCLDataEntry((long) Objects.requireNonNull(temperatureGraphViewModel.getLatestTemperature().getValue()).getTime().getTime(), 0.1, 0.1, 0.1, Objects.requireNonNull(temperatureGraphViewModel.getLatestTemperature().getValue()).getTemperature()));
                    }
                    for (Temperature temperature : temperatures) {
                        data.add(new GraphsFragment.OHCLDataEntry(temperature.getTime().getTime(), temperature.getTemperature(), temperature.getTemperature(), temperature.getTemperature(), temperature.getTemperature()));
                    }
                    table.addData(data);
                }
            }
        });
        return table;
    }

    public void initTemperatureChart()
    {
        APIlib.getInstance().setActiveAnyChartView(temperatureChart);
        Stock stock4 = AnyChart.stock();
        Plot plot4 = stock4.plot(0);
        plot4.ema(temperatureGraph().mapAs("{value: 'close'}"), 1d, StockSeriesType.LINE);
        plot4.yAxis(0).labels().format("{%Value} °C");
        stock4.title().text("Temperature");
        stock4.title().enabled(true);
        temperatureChart.setChart(stock4);
    }
    private void updateMeasurements(){
        temperatureGraphViewModel.initUserInfo();
        temperatureGraphViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            temperatureGraphViewModel.updateHistoryData(userInfo.getGreenhouseID());
        });
    }
}
