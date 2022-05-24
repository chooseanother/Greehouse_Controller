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

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.HighLowDataEntry;
import com.anychart.charts.Stock;
import com.anychart.core.stock.Plot;
import com.anychart.data.Table;
import com.anychart.enums.StockSeriesType;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.databinding.FragmentGraphsBinding;
import com.example.greehousecontroller.ui.viewmodel.GraphsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GraphsFragment extends Fragment {

    private FragmentGraphsBinding binding;
    GraphsViewModel graphsViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);
        binding = FragmentGraphsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnyChartView anyChartView = binding.anyChartView;
        updateMeasurements();
        Table table = Table.instantiate("x");

        List<DataEntry> data = new ArrayList<>();
        anyChartView.setProgressBar(binding.progressBar);


        graphsViewModel.getTemperatureHistoryData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Temperature>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Temperature> temperatures) {
                for (int i = 0; i < 1; i++) {
                    data.add(new OHCLDataEntry((long) Objects.requireNonNull(graphsViewModel.getLatestTemperature().getValue()).getTime(), 0.1, 0.1, 0.1, Objects.requireNonNull(graphsViewModel.getLatestTemperature().getValue()).getTemperature()));
                }
                for (Temperature temperature : temperatures) {
                    data.add(new OHCLDataEntry(temperature.getTime(), temperature.getTemperature(), temperature.getTemperature(), temperature.getTemperature(), temperature.getTemperature()));
                }
                table.addData(data);


                Stock stock = AnyChart.stock();

                Plot plot = stock.plot(0);

                plot.ema(table.mapAs("{value: 'close'}"), 1d, StockSeriesType.LINE);

                plot.title().enabled(true);
                plot.title().text("Temperature");
                anyChartView.setChart(stock);
            }
        });
    }

    private void updateMeasurements(){
        // TODO: Figure out how to handle greenhouseId
        graphsViewModel.updateTemperatureHistoryData("test");
    }

    private static class OHCLDataEntry extends HighLowDataEntry {
        OHCLDataEntry(Long x, Double open, Double high, Double low, Double close) {
            super(x, high, low);
            setValue("open", open);
            setValue("close", close);
        }
    }


}