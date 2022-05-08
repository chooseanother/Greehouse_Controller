package com.example.greehousecontroller.ui.pots;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.greehousecontroller.Repository.Measurements.Humidity.HumidityRepository;

public class EditPotViewModel extends AndroidViewModel {

    //private final PotRepository potRepository;

    public EditPotViewModel(Application application) {
        super(application);
        //potRepository = PotRepository.getInstance(application);
    }

    public void init(String name) {
        //potRepository.init(name);
    }
}