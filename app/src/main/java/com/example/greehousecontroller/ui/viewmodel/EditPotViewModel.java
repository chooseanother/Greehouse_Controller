package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.repository.PotRepository;

public class EditPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;

    public EditPotViewModel(Application application) {
        super(application);
        potRepository = PotRepository.getInstance(application);
    }

    public String init(String greenhouseId, int id) {
        return potRepository.init(greenhouseId, id);
    }

    public MutableLiveData<Pot> getCurrentPot() {
        return potRepository.getCurrentPot();
    }

    public String updateCurrentPot(String greenhouseId, int id, String name, double minimumThreshold) {
        if(checkForThresholdInput(minimumThreshold) && checkForNameInput(name)){
            String response = potRepository.updateCurrentPot(greenhouseId, id, name, minimumThreshold);
            return response;
        }
        else
        {
            return "Check the input";
        }
    }

    private boolean checkForThresholdInput(double minimumThreshold){
        if(minimumThreshold <= 100.0 && minimumThreshold >= 0.0){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean checkForNameInput(String name){
        if(name.length() > 100 || name.equals("") || name == null){
            return false;
        }
        else{
            return true;
        }
    }
}