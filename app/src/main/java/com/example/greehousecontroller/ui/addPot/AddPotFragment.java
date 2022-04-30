package com.example.greehousecontroller.ui.addPot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.adapters.HomeFragmentAdapter;

import org.w3c.dom.Text;

public class AddPotFragment extends Fragment {

    private AddPotViewModel viewModel;
    private EditText namePotTextView;
    private EditText minimalHumidityTextView;
    private TextView errorMessage;
    private Button savePotButton;
    private Button cancelPotButton;
    private NavController navController;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(AddPotViewModel.class);
        root = inflater.inflate(R.layout.fragment_add_pot, container, false);
        namePotTextView = root.findViewById(R.id.potNameTextView);
        minimalHumidityTextView = root.findViewById(R.id.minimalHumidityTextView);
        errorMessage = root.findViewById(R.id.errorMessageTextView);
        savePotButton = root.findViewById(R.id.saveAddPotButton);
        cancelPotButton = root.findViewById(R.id.cancelPotButton);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

        savePotButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Should include sensor in the future
                if(viewModel.validInput(namePotTextView.getText().toString(),
                        minimalHumidityTextView.getText().toString()) == null){
                    navController.navigate(R.id.nav_home);
                }
                else{
                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setText(viewModel.validInput(namePotTextView.getText().toString(),
                            minimalHumidityTextView.getText().toString()));
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
}