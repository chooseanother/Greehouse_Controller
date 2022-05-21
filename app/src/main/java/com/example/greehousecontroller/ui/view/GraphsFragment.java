package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.HighLowDataEntry;
import com.anychart.charts.Stock;
import com.anychart.core.annotations.Line;
import com.anychart.core.stock.Plot;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.data.Table;
import com.anychart.enums.StockSeriesType;
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.CO2;
import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.databinding.FragmentGraphsBinding;
import com.example.greehousecontroller.ui.viewmodel.GraphsViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphsFragment extends Fragment {
    private FragmentGraphsBinding binding;
    GraphsViewModel graphsViewModel;
    Spinner spinner;
    AnyChartView co2Chart;
    AnyChartView temperatureChart;
    AnyChartView humidityChart;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);
        binding = FragmentGraphsBinding.inflate(inflater, container, false);
        spinner = binding.spinner;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        temperatureChart = binding.temperatureChart;
        co2Chart = binding.co2Chart;
        humidityChart = binding.humidityChart;
        updateMeasurements();
        spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.Graphs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinner.getSelectedItem().toString()) {
                    case "Temperature":
                            APIlib.getInstance().setActiveAnyChartView(temperatureChart);
                            Stock stock = AnyChart.stock();
                            Plot plot = stock.plot(0);
                            plot.ema(temperatureGraph().mapAs("{value: 'close'}"), 1d, StockSeriesType.LINE);
                            plot.yAxis(0).labels().format("{%Value} °C");
                            stock.title().text("Temperature");
                            stock.title().enabled(true);
                            temperatureChart.setChart(stock);
                            temperatureChart.setVisibility(View.VISIBLE);
                            co2Chart.setVisibility(View.GONE);
                            humidityChart.setVisibility(View.GONE);
                        break;
                    case "Humidity":
                            APIlib.getInstance().setActiveAnyChartView(humidityChart);
                            Stock stock2 = AnyChart.stock();
                            Plot plot2 = stock2.plot(0);
                            plot2.ema(humidityGraph().mapAs("{value: 'close'}"), 1d, StockSeriesType.LINE);
                            plot2.yAxis(0).labels().format("{%Value} %");
                            stock2.title().text("Humidity");
                            stock2.title().enabled(true);
                            humidityChart.setChart(stock2);
                            temperatureChart.setVisibility(View.GONE);
                            co2Chart.setVisibility(View.GONE);
                            humidityChart.setVisibility(View.VISIBLE);
                        break;
                    case "CO2":
                            APIlib.getInstance().setActiveAnyChartView(co2Chart);
                            Stock stock3 = AnyChart.stock();
                            Plot plot3 = stock3.plot(0);
                            plot3.ema(co2Graph().mapAs("{value: 'close'}"), 1d, StockSeriesType.LINE);
                            plot3.yAxis(0).labels().format("{%Value} ppm");
                            stock3.title().text("CO2");
                            stock3.title().enabled(true);
                            co2Chart.setChart(stock3);
                            temperatureChart.setVisibility(View.GONE);
                            co2Chart.setVisibility(View.VISIBLE);
                            humidityChart.setVisibility(View.GONE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public Table temperatureGraph()
    {
        Table table = Table.instantiate("x");
        List<DataEntry> data = new ArrayList<>();
        graphsViewModel.getTemperatureHistoryData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Temperature>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Temperature> temperatures) {
                if (temperatures.size() > 0) {
                    for (int i = 0; i < 1; i++) {
                        data.add(new OHCLDataEntry((long) Objects.requireNonNull(graphsViewModel.getLatestTemperature().getValue()).getTime().getTime(), 0.1, 0.1, 0.1, Objects.requireNonNull(graphsViewModel.getLatestTemperature().getValue()).getTemperature()));
                    }
                    for (Temperature temperature : temperatures) {
                        data.add(new OHCLDataEntry(temperature.getTime().getTime(), temperature.getTemperature(), temperature.getTemperature(), temperature.getTemperature(), temperature.getTemperature()));
                    }
                    table.addData(data);
                }
            }
        });
        return table;
    }
    public Table co2Graph()
    {
        Table table = Table.instantiate("x");

        List<DataEntry> data = new ArrayList<>();
        graphsViewModel.getCo2HistoryData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CO2>>() {
            @Override
            public void onChanged(@Nullable ArrayList<CO2> co2s) {
                if (co2s.size() > 0) {
                    for (int i = 0; i < 1; i++) {
                        data.add(new OHCLDataEntry((long) Objects.requireNonNull(graphsViewModel.getLatestCO2().getValue()).getTime(), 0.1, 0.1, 0.1, Objects.requireNonNull(graphsViewModel.getLatestCO2().getValue()).getCO2()));
                    }
                    for (CO2 co2 : co2s) {
                        data.add(new OHCLDataEntry(co2.getTime(), co2.getCO2(), co2.getCO2(), co2.getCO2(), co2.getCO2()));
                    }

                    table.addData(data);
                }
            }
        });
        return table;
    }
    public Table humidityGraph(){
        Table table = Table.instantiate("x");
        List<DataEntry> data = new ArrayList<>();
        graphsViewModel.getHumidityHistoryData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Humidity>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Humidity> humidities) {
                if (humidities.size() > 0) {
                    for (int i = 0; i < 1; i++) {
                        data.add(new OHCLDataEntry((long) Objects.requireNonNull(graphsViewModel.getLatestHumidity().getValue()).getTime(), 0.1, 0.1, 0.1, Objects.requireNonNull(graphsViewModel.getLatestHumidity().getValue()).getHumidity()));
                    }
                    for (Humidity humidity : humidities) {
                        data.add(new OHCLDataEntry(humidity.getTime(), humidity.getHumidity(), humidity.getHumidity(), humidity.getHumidity(), humidity.getHumidity()));
                    }
                    table.addData(data);
                }
            }
        });
        return table;
    }


    private void updateMeasurements(){
        // TODO: Figure out how to handle greenhouseId
        graphsViewModel.updateHistoryData("0004A30B00E7E7C1");
    }

    private static class OHCLDataEntry extends HighLowDataEntry {
        OHCLDataEntry(Long x, Double open, Double high, Double low, Double close) {
            super(x, high, low);
            setValue("open", open);
            setValue("close", close);
        }
    }


}