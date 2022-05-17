package com.example.greehousecontroller.ui.view;

import android.os.Bundle;
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
import com.example.greehousecontroller.databinding.FragmentEditPotBinding;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.ui.viewmodel.EditPotViewModel;

import java.text.DecimalFormat;

public class EditPotFragment extends Fragment {

    private FragmentEditPotBinding binding;
    private EditPotViewModel viewModel;
    private EditText potName;
    private EditText minimalThreshold;
    private Button saveButton;
    private Button cancelButton;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(EditPotViewModel.class);
        //"test" should be replaced by actual id in the future
        viewModel.init("test", Integer.parseInt(getArguments().getString("id")));
        binding = FragmentEditPotBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        potName = root.findViewById(R.id.pot_name_edit_text);
        saveButton = root.findViewById(R.id.save_pot_button);
        minimalThreshold = root.findViewById(R.id.minimum_threshold_edit_text);
        cancelButton = root.findViewById(R.id.cancel_edit_pot_button);

        MutableLiveData<Pot> pot = viewModel.getCurrentPot();
        pot.observe(getViewLifecycleOwner(), currentPot -> {
            if(currentPot != null){
                potName.setText(pot.getValue().getName());
                DecimalFormat df = new DecimalFormat("0.00");
                minimalThreshold.setText(df.format(pot.getValue().getLowerMoistureThreshold()) + " %");
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //calling viewModel to check for input and updating pot in the DB
                if(viewModel.updateCurrentPot("test", pot.getValue().getId(), potName.getText().toString(), Double.parseDouble(minimalThreshold.getText().toString()))){
                ((MainActivity)getActivity()).navController.navigate(R.id.nav_home);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).navController.navigate(R.id.nav_home);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}