package com.example.greehousecontroller.ui.pots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.databinding.FragmentPotsBinding;

public class PotsFragment extends Fragment {

    private FragmentPotsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PotsViewModel potsViewModel =
                new ViewModelProvider(this).get(PotsViewModel.class);

        binding = FragmentPotsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPots;
        potsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}