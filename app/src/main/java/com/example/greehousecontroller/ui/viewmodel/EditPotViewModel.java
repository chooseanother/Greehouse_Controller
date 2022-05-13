package com.example.greehousecontroller.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;


import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.data.repository.PotRepository;

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
        return potRepository.getPots().getValue().get(0);
    }

    public boolean updateCurrentPot(String greenhouseId, int id, String name, float minimumThreshold) {
        return potRepository.updateCurrentPot(greenhouseId, id, name, minimumThreshold);
    }
}