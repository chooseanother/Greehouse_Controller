package com.example.greehousecontroller.ui.pots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentEditPotBinding;

public class EditPotFragment extends Fragment {

    private FragmentEditPotBinding binding;
    private EditPotViewModel viewModel;
    private TextView potName;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(EditPotViewModel.class);
        viewModel.init(getArguments().getString("name"));
        binding = FragmentEditPotBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        potName = root.findViewById(R.id.pot_name);
        //potName.setText(viewModel.getPotName());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}