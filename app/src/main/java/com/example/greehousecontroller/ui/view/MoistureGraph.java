package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Moisture;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.databinding.FragmentMoisturePotsGraphsBinding;
import com.example.greehousecontroller.databinding.MoistureGraph1Binding;
import com.example.greehousecontroller.ui.viewmodel.MoistureViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MoistureGraph extends Fragment {

    private MoistureGraph1Binding binding;
    private MoistureViewModel moistureViewModel;
    private AnyChartView moistureChart;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MoistureGraph1Binding.inflate(inflater, container, false);
        moistureViewModel = new ViewModelProvider(this).get(MoistureViewModel.class);
        moistureChart = binding.humidityChart;
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMoistureChart();
    }
    public Table moistureGraph(){
        Table table = Table.instantiate("x");
        List<DataEntry> data = new ArrayList<>();
        moistureViewModel.getMoistureHistoryData().observe(getViewLifecycleOwner(), new Observer<List<Moisture>>() {
            @Override
            public void onChanged(List<Moisture> moistureHistoryData) {
                if(moistureViewModel.getLatestHumidity().getValue() != null) {
                    for (int i = 0; i < 1; i++) {
                        data.add(new GraphsFragment.OHCLDataEntry((long) Objects.requireNonNull(moistureViewModel.getLatestHumidity().getValue()).getTime(), 0.1, 0.1, 0.1, moistureViewModel.getLatestHumidity().getValue().getMoisture()));
                    }
                    for (Moisture moisture : moistureHistoryData) {
                        data.add(new GraphsFragment.OHCLDataEntry(moisture.getTime(), moisture.getMoisture(), moisture.getMoisture(), moisture.getMoisture(), moisture.getMoisture()));
                    }
                    table.addData(data);
                }
            }
        });
        return table;
    }
    public void initMoistureChart(){
        APIlib.getInstance().setActiveAnyChartView(moistureChart);
        Stock stock2 = AnyChart.stock();
        Plot plot2 = stock2.plot(0);
        plot2.ema(moistureGraph().mapAs("{value: 'close'}"), 1d, StockSeriesType.LINE);
        plot2.yAxis(0).labels().format("{%Value} %");
        stock2.title().text("Moisture");
        stock2.title().enabled(true);
        moistureChart.setChart(stock2);
    }

}
