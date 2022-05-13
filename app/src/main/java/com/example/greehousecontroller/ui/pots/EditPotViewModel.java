package com.example.greehousecontroller.ui.pots;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.greehousecontroller.Repository.PotRepository.PotRepository;
import com.example.greehousecontroller.model.Pot;

public class EditPotViewModel extends AndroidViewModel {

    private final PotRepository potRepository;

    public EditPotViewModel(Application application) {
        super(application);
        potRepository = PotRepository.getInstance();
    }

    public void init(String greenhouseId, int id) {
        potRepository.init(greenhouseId, id);
    }

    public Pot getCurrentPot() {
        return new Pot("23234", 11, 234); //potRepository.getCurrentPot().getValue();
    }

    public boolean updateCurrentPot(String greenhouseId, int id, String name, String minimumThreshold) {
        return potRepository.updateCurrentPot(greenhouseId, id, name, minimumThreshold);
    }
}