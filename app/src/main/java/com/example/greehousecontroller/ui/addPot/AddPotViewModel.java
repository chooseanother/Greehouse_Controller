package com.example.greehousecontroller.ui.addPot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddPotViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddPotViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pots fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public String validInput(String name, String minimumHumidity){
        //Accessing repository in the future
        if(name.equals("")|| name == null){
           return "Please insert name";
        }
        else if(minimumHumidity.equals("") || minimumHumidity == null){
            return "Please insert minimum humidity";
        }
        else{
            return null;
        }
    }
}