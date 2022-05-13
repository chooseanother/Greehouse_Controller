package com.example.greehousecontroller.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GraphsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GraphsViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is graphs fragment");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}