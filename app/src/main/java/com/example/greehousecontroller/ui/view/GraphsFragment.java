package com.example.greehousecontroller.ui.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Temperature;
import com.example.greehousecontroller.databinding.FragmentGraphsBinding;
import com.example.greehousecontroller.ui.viewmodel.GraphsViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GraphsFragment extends Fragment {

    private FragmentGraphsBinding binding;
    LineChart lineChart;
    GraphsViewModel graphsViewModel;
    private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);
        binding = FragmentGraphsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Spinner timespanSpinner = view.findViewById(R.id.dates_spinner);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(view.getContext(), R.array.timespans, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        timespanSpinner.setAdapter(adapter);

        lineChart = view.findViewById(R.id.temperature_lineChart);
        updateMeasurements();
        loadTemperatureChartData(100,100);
        lineChart.animate();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void loadTemperatureChartData(int count,int range ) {
        ArrayList<Entry> yVals1 = new ArrayList<>();
        LineDataSet set1 ;
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new MyAxisFormatter());
        for(int i = Objects.requireNonNull(graphsViewModel.getTemperatureHistoryData().getValue()).size()-1; i > 1; i--){
            System.out.println(graphsViewModel.getTemperatureHistoryData().getValue().get(i).getTime().getTime());

            long now = TimeUnit.MILLISECONDS.toMinutes(graphsViewModel.getTemperatureHistoryData().getValue().get(i).getTime().getTime());
            yVals1.add(new Entry(now,(float)graphsViewModel.getTemperatureHistoryData().getValue().get(i).getTemperature()));
        }
        set1 = new LineDataSet(yVals1,"Temperature");
        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawVerticalHighlightIndicator(false);
        set1.setColor(0xFFFF0000);

        LineData data = new LineData(set1);

        lineChart.setData(data);
    }
    private void updateMeasurements(){
        // TODO: Figure out how to handle greenhouseId
        graphsViewModel.updateTemperatureHistoryData("test");
    }
    private static class MyAxisFormatter extends ValueFormatter {
        private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm:ss", Locale.ENGLISH);

        @Override
        public String getFormattedValue(float value) {
            long millis = TimeUnit.HOURS.toMillis((long) value);
            return mFormat.format(new Date(millis));
        }
    }
}