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

import com.example.greehousecontroller.databinding.FragmentGraphsBinding;

public class GraphsFragment extends Fragment {

    private FragmentGraphsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        GraphsViewModel graphsViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);

        binding = FragmentGraphsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGraphs;
        graphsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}