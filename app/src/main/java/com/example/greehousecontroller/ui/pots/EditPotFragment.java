package com.example.greehousecontroller.ui.pots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greehousecontroller.MainActivity;
import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentEditPotBinding;
import com.example.greehousecontroller.model.Pot;

public class EditPotFragment extends Fragment {

    private FragmentEditPotBinding binding;
    private EditPotViewModel viewModel;
    private EditText potName;
    private EditText minimalThreshold;
    private Button saveButton;
    private Button deleteButton;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(EditPotViewModel.class);
        //"test" should be replaced by actual id in the future
        viewModel.init("test", Integer.valueOf(getArguments().getString("id")));
        Pot pot = viewModel.getCurrentPot();
        binding = FragmentEditPotBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        potName = root.findViewById(R.id.pot_name_edit_text);
        potName.setText(pot.getName());

        minimalThreshold = root.findViewById(R.id.minimum_threshold_edit_text);
        minimalThreshold.setText(pot.getMinimalHumidity());

        saveButton = root.findViewById(R.id.save_pot_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //calling viewModel to check for input and updating pot in the DB
                if(viewModel.updateCurrentPot("test", pot.getId(), potName.getText().toString(), minimalThreshold.getText().toString())){
                ((MainActivity)getActivity()).navController.navigate(R.id.nav_home);
                }
            }
        });

        deleteButton = root.findViewById(R.id.cancel_edit_pot_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //calling viewModel to delete pot from the DB
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