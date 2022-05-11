package com.example.greehousecontroller.ui.graphs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentGraphsBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Map;

public class GraphsFragment extends Fragment {

    private FragmentGraphsBinding binding;
    LineChart lineChart;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        GraphsViewModel graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);

        binding = FragmentGraphsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lineChart = root.findViewById(R.id.temperature_lineChart);
        loadTemperatureChartData(40,60);
        lineChart.animate();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void loadTemperatureChartData(int count,int range ) {
        ArrayList<Entry> yVals1 = new ArrayList<>();
        for(int i = 0;i < count;i++){
            float val = (float) (Math.random()*range)+250;
            yVals1.add(new Entry(i,val));
        }

        LineDataSet set1 ;

        set1= new LineDataSet(yVals1,"Temperature");
        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawVerticalHighlightIndicator(false);
        set1.setColor(0xFFFF0000);

        LineData data = new LineData(set1);

        lineChart.setData(data);
    }
}