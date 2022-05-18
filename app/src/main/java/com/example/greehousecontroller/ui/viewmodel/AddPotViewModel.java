package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.greehousecontroller.data.repository.PotRepository;

public class AddPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;

    public AddPotViewModel(Application application) {
        super(application);
        potRepository = PotRepository.getInstance(application);
    }

    public String validInput(String greenhouseId, String name, double minimumHumidity){
            if(checkForNameInput(name) && checkForThresholdInput(minimumHumidity)){
                String response = addPot(greenhouseId, name, minimumHumidity);
                return response;
            }
            else
            {
                return "Check the input";
            }
    }

    public String addPot(String greenhouseId, String name, double minimumHumidity){
        return potRepository.addPot(greenhouseId, name, minimumHumidity);
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