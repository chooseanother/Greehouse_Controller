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

    public void init(String name) {
        potRepository.init(name);
    }

    public Pot getCurrentPot() {
        return potRepository.getCurrentPot().getValue();
    }

    public void deleteCurrentPot() {
        potRepository.deleteCurrentPot();
    }

    public boolean updateCurrentPot(String name, String minimumThreshold) {
        return potRepository.updateCurrentPot(name, minimumThreshold);
    }
}