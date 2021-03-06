package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentEditGreenhouseIdBinding;
import com.example.greehousecontroller.ui.viewmodel.EditGreenhouseIdViewModel;

public class EditGreenhouseIdFragment extends Fragment {
    private EditGreenhouseIdViewModel viewModel;
    private FragmentEditGreenhouseIdBinding binding;
    private String greenhouseId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(EditGreenhouseIdViewModel.class);
        viewModel.initUserInfo();
        binding = FragmentEditGreenhouseIdBinding.inflate(inflater, container, false);
        observerUserInfo();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bindSaveButton();
    }

    private void observerUserInfo() {
        viewModel.getCurrentUserInto().observe(getViewLifecycleOwner(), userInfo -> {
            greenhouseId = userInfo.getGreenhouseID();
            binding.editGreenhouseIdCurrentIdTextView.setText(greenhouseId);
        });
    }

    private void bindSaveButton() {
        EditText newIdText = binding.editGreenhouseIdNewEditText;
        binding.editGreenhouseIdSave.setOnClickListener(view -> {
            String oldGreenhouseId = greenhouseId;
            String newGreenhouseId = newIdText.getText().toString();
            if (oldGreenhouseId.equals(newGreenhouseId)) {
                Toast.makeText(getContext(), R.string.change_greenhouse_id_notify_same_id, Toast.LENGTH_SHORT).show();
            } else {
                viewModel.unsubscribeFromGreenhouse(oldGreenhouseId);
                viewModel.saveGreenHouseId(newGreenhouseId);
                viewModel.subscribeToGreenhouse(newGreenhouseId);
                newIdText.setText("");
                viewModel.clearCachedDate();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
