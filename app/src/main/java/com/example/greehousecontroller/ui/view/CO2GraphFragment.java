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
import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.databinding.Co2GraphBinding;
import com.example.greehousecontroller.ui.viewmodel.CO2GraphViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CO2GraphFragment extends Fragment {
    private Co2GraphBinding binding;
    private AnyChartView co2Chart;
    private CO2GraphViewModel co2GraphViewModel;
    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = Co2GraphBinding.inflate(inflater, container, false);
        co2GraphViewModel = new ViewModelProvider(this).get(CO2GraphViewModel.class);
        setBindings();
        co2GraphViewModel.loadCachedData();
        updateMeasurements();
        initCO2Chart();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setBindings() {
        co2Chart = binding.co2Chart;
        progressBar = binding.co2ProgressBar;
    }

    public void initCO2Chart() {
        co2Chart.setProgressBar(progressBar);
        APIlib.getInstance().setActiveAnyChartView(co2Chart);
        Stock stock3 = AnyChart.stock();
        Plot plot3 = stock3.plot(0);
        plot3.ema(co2Graph().mapAs("{value: 'close'}"), 1d, StockSeriesType.LINE);
        plot3.yAxis(0).labels().format("{%Value} ppm");
        stock3.title().text("CO2");
        stock3.title().enabled(true);
        co2Chart.setChart(stock3);
    }

    public Table co2Graph() {
        Table table = Table.instantiate("x");

        List<DataEntry> data = new ArrayList<>();
        co2GraphViewModel.getCo2HistoryData().observe(getViewLifecycleOwner(), new Observer<List<CO2>>() {

            @Override
            public void onChanged(@Nullable List<CO2> co2s) {
                if (co2s != null) {
                    if (co2s.size() > 0) {
                        for (int i = 0; i < 1; i++) {
                            long time = (long) Objects.requireNonNull(co2GraphViewModel.getLatestCO2().getValue()).getTime();
                            double measurement = Objects.requireNonNull(co2GraphViewModel.getLatestCO2().getValue()).getCo2Measurement();
                            data.add(new GraphsFragment.OHCLDataEntry(time, 0.1, 0.1, 0.1, measurement));
                        }
                        for (CO2 co2 : co2s) {
                            data.add(new GraphsFragment.OHCLDataEntry(co2.getTime(), co2.getCo2Measurement(), co2.getCo2Measurement(), co2.getCo2Measurement(), co2.getCo2Measurement()));
                        }

                        table.addData(data);
                    }
                }
            }
        });
        return table;
    }

    private void updateMeasurements() {
        co2GraphViewModel.initUserInfo();
        co2GraphViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            co2GraphViewModel.updateHistoryData(userInfo.getGreenhouseID());
        });
    }
}
