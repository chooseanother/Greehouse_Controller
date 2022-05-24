package com.example.greehousecontroller.ui.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.databinding.FragmentAddPotBinding;
import com.example.greehousecontroller.ui.viewmodel.AddPotViewModel;

public class AddPotFragment extends Fragment {

    private AddPotViewModel viewModel;
    private FragmentAddPotBinding binding;
    private EditText namePotTextView;
    private EditText minimalHumidityTextView;
    private Button savePotButton;
    private Button cancelPotButton;
    private NavController navController;
    private View root;
    private String greenhouseid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(AddPotViewModel.class);

        binding = FragmentAddPotBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        getGreenhouseID();
        namePotTextView = root.findViewById(R.id.potNameTextView);
        minimalHumidityTextView = root.findViewById(R.id.minimalHumidityTextView);
        minimalHumidityTextView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        savePotButton = root.findViewById(R.id.saveAddPotButton);
        cancelPotButton = root.findViewById(R.id.cancelPotButton);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

        savePotButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Should include sensor in the future
                String result = viewModel.validInput(greenhouseid, namePotTextView.getText().toString(),
                        Double.parseDouble(minimalHumidityTextView.getText().toString()));
                if(result.equals("")){
                    navController.navigate(R.id.nav_home);
                }
                else{
                    Toast.makeText(getContext(), ""+ result, Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelPotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_home);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    private void getGreenhouseID(){
        viewModel.initUserInfo();
        viewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            greenhouseid = userInfo.getGreenhouseID();
        });
    }
}