package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.MainActivity;
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.databinding.FragmentEditPotBinding;
import com.example.greehousecontroller.ui.viewmodel.EditPotViewModel;

import java.text.DecimalFormat;

public class EditPotFragment extends Fragment {
    private FragmentEditPotBinding binding;
    private EditPotViewModel viewModel;
    private View root;
    private String greenhouseId;

    //xml elements
    private EditText potName;
    private EditText minimalThreshold;
    private Button saveButton;
    private Button cancelButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(EditPotViewModel.class);
        setUpTextViews();
        viewModel.init(greenhouseId, Integer.parseInt(getArguments().getString("id")));
        binding = FragmentEditPotBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        setUpBinding();
        setUpTextViews();
        setUpCancelButton();

        return root;
    }

    private void setUpTextViews(){
        viewModel.initUserInfo();
        viewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            greenhouseId = userInfo.getGreenhouseID();
            viewModel.init(greenhouseId, Integer.parseInt(getArguments().getString("id")));
            MutableLiveData<Pot> pot = viewModel.getCurrentPot();
            pot.observe(getViewLifecycleOwner(), currentPot -> {
                if (currentPot != null) {
                    potName.setText(pot.getValue().getName());
                    DecimalFormat df = new DecimalFormat("0.0");
                    minimalThreshold.setText(df.format(pot.getValue().getLowerMoistureThreshold()));
                }
            });
            setUpSaveButton(pot);
        });
    }

    private void setUpCancelButton(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).navController.navigate(R.id.navigationHome);
            }
        });
    }

    private void setUpSaveButton(MutableLiveData<Pot> pot){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling viewModel to check for input and updating pot in the D
                if (pot.getValue() != null) {
                    boolean response = viewModel.updateCurrentPot(greenhouseId, pot.getValue().getId(), potName.getText().toString(), minimalThreshold.getText().toString());
                    if (response) {
                        ((MainActivity) getActivity()).navController.navigate(R.id.navigationHome);
                    }
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpBinding(){
        potName = binding.editPotNameEditText;
        saveButton = binding.editPotSaveButton;
        minimalThreshold = binding.editPotMinimumThresholdEditText;
        minimalThreshold.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        cancelButton = binding.editPotCancelButton;
    }
}