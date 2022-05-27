package com.example.greehousecontroller.ui.view;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anychart.chart.common.dataentry.HighLowDataEntry;
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentGraphsBinding;
import com.example.greehousecontroller.ui.viewmodel.GraphsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class GraphsFragment extends Fragment {
    private FragmentGraphsBinding binding;
    GraphsViewModel graphsViewModel;
    BottomNavigationView bottomAppBar;
    NavController navController;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);
        binding = FragmentGraphsBinding.inflate(inflater, container, false);
        bottomAppBar = binding.bottomNavigationGraphs;
        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(),R.id.graphsNavHostFragment);
        graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);
        bottomAppBar = binding.bottomNavigationGraphs;
        bottomAppBar.setOnNavigationItemSelectedListener( item -> {
            switch (item.getItemId()) {
                case R.id.navigation_temperature:
                        navController.navigate(R.id.navigation_temperature_graph);
                    return true;
                case R.id.navigation_humidity:
                        navController.navigate(R.id.navigation_humidity_graph);
                    return true;
                case R.id.navigation_co2:
                        navController.navigate(R.id.navigation_co2_graph);
                    return true;
            }
            return false;
        });
    }


    static class OHCLDataEntry extends HighLowDataEntry {
        OHCLDataEntry(Long x, Double open, Double high, Double low, Double close) {
            super(x, high, low);
            setValue("open", open);
            setValue("close", close);
        }
    }


}