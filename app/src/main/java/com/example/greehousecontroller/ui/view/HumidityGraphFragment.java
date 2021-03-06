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
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.databinding.HumidityGraphBinding;
import com.example.greehousecontroller.ui.viewmodel.HumidityGraphViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HumidityGraphFragment extends Fragment {
    private HumidityGraphBinding binding;
    private HumidityGraphViewModel humidityViewModel;
    private AnyChartView humidityChart;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HumidityGraphBinding.inflate(inflater, container, false);
        humidityViewModel = new ViewModelProvider(this).get(HumidityGraphViewModel.class);
        setBindings();
        humidityViewModel.loadCachedData();
        updateMeasurements();
        initHumidityChart();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setBindings() {
        humidityChart = binding.humidityChart;
        progressBar = binding.humidityProgressBar;
    }


    public Table humidityGraph() {
        Table table = Table.instantiate("x");
        List<DataEntry> data = new ArrayList<>();
        humidityViewModel.getHumidityHistoryData().observe(getViewLifecycleOwner(), new Observer<List<Humidity>>() {
            @Override
            public void onChanged(@Nullable List<Humidity> humidities) {
                if (humidities != null) {
                    if (humidities.size() > 0) {
                        for (int i = 0; i < 1; i++) {
                            long localTime = (long) Objects.requireNonNull(humidityViewModel.getLatestHumidity().getValue()).getLocalTime();
                            double measurement = humidityViewModel.getLatestHumidity().getValue().getHumidity();
                            data.add(new GraphsFragment.OHCLDataEntry(localTime, 0.1, 0.1, 0.1, measurement));
                        }
                        for (Humidity humidity : humidities) {
                            data.add(new GraphsFragment.OHCLDataEntry(humidity.getLocalTime(), humidity.getHumidity(), humidity.getHumidity(), humidity.getHumidity(), humidity.getHumidity()));
                        }
                        table.addData(data);
                    }
                }
            }
        });
        return table;
    }

    public void initHumidityChart() {
        humidityChart.setProgressBar(progressBar);
        APIlib.getInstance().setActiveAnyChartView(humidityChart);
        Stock stock2 = AnyChart.stock();
        Plot plot2 = stock2.plot(0);
        plot2.ema(humidityGraph().mapAs("{value: 'close'}"), 1d, StockSeriesType.LINE);
        plot2.yAxis(0).labels().format("{%Value} %");
        stock2.title().text("Humidity");
        stock2.title().enabled(true);
        humidityChart.setChart(stock2);
    }

    private void updateMeasurements() {
        humidityViewModel.initUserInfo();
        humidityViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            humidityViewModel.updateHistoryData(userInfo.getGreenhouseID());
            initHumidityChart();
        });
    }
}
