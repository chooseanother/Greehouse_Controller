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

    public void init(String greenhouseId, int id) {
        potRepository.init(greenhouseId, id);
    }

    public MutableLiveData<Pot> getCurrentPot() {
        return potRepository.getCurrentPot();
    }

    public void updateCurrentPot(String greenhouseId, int id, String name, double minimumThreshold) {
        potRepository.updateCurrentPot(greenhouseId, id, name, minimumThreshold);
    }
}