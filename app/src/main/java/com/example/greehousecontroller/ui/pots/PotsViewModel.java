package com.example.greehousecontroller.ui.pots;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PotsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PotsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pots fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}