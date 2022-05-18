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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentGraphsBinding;
import com.example.greehousecontroller.ui.viewmodel.GraphsViewModel;
import com.github.mikephil.charting.charts.LineChart;
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
    Spinner datesSpinner;
    private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);
        binding = FragmentGraphsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.timespans,
                android.R.layout.simple_spinner_item);
        lineChart = view.findViewById(R.id.temperature_lineChart);
        datesSpinner = view.findViewById(R.id.dates_spinner);
        datesSpinner.setAdapter(adapter);
        updateMeasurements();
        loadTemperatureChartData(100,100);
        lineChart.animate();
        datesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void loadTemperatureChartData(int count,int range ) {
        ArrayList<Entry> yVals1 = new ArrayList<>();
        ArrayList<Entry> yVals2 = new ArrayList<>();

        LineDataSet set1;
        LineDataSet set2;
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new Hours());
        for(int i = Objects.requireNonNull(graphsViewModel.getTemperatureHistoryData().getValue()).size()-1; i > 1; i--){
            yVals2.add(new Entry(graphsViewModel.getTemperatureHistoryData().getValue().get(i).getTime().getTime(),(float) 0));
            yVals1.add(new Entry(graphsViewModel.getTemperatureHistoryData().getValue().get(i).getTime().getTime(),(float)graphsViewModel.getTemperatureHistoryData().getValue().get(i).getTemperature()));
        }
        set1 = new LineDataSet(yVals1,"Temperature");
        set1.setDrawValues(false);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawVerticalHighlightIndicator(false);
        set1.setColor(0xFFFF0000);


        set2 = new LineDataSet(yVals2,"");
        set2.setDrawValues(false);
        set2.setDrawCircles(false);
        set2.setDrawHorizontalHighlightIndicator(false);
        set2.setDrawVerticalHighlightIndicator(false);
        set2.setColor(0X00);

        LineData data = new LineData(set1,set2);

        lineChart.setData(data);

    }
    private void setValuesOfGraph()
    {

    }
    private void updateMeasurements(){
        // TODO: Figure out how to handle greenhouseId
        graphsViewModel.updateTemperatureHistoryData("test");
    }
    private static class Hours extends ValueFormatter {
        private final SimpleDateFormat mFormat = new SimpleDateFormat("dd.MM HH:mm:ss", Locale.ENGLISH);

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(new Date((long) value));
        }
    }

}