package com.example.greehousecontroller.ui.addPot;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.greehousecontroller.Repository.PotRepository.PotRepository;

public class AddPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;

    public AddPotViewModel(Application application) {
        super(application);
        potRepository = PotRepository.getInstance();
    }

    public String validInput(String greenhouseId, String name, String minimumHumidity){
        if(name.equals("")|| name == null){
           return "Please insert name";
        }
        else if(minimumHumidity.equals("") || minimumHumidity == null){
            return "Please insert minimum moisture";
        }
        else{
            addPot(greenhouseId, name, minimumHumidity);
            return null;
        }
    }

    public boolean addPot(String greenhouseId, String name, String minimumHumidity){
        return potRepository.addPot(greenhouseId, name, minimumHumidity);
    }
}