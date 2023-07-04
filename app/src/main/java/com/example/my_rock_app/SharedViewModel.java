package com.example.my_rock_app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SharedViewModel extends ViewModel{
    private MutableLiveData<Boolean> shouldAnalyze = new MutableLiveData<>();

    public SharedViewModel(int img, String name) {
        super(img, name);
    }

    public LiveData<Boolean> getShouldAnalyze(){
        return shouldAnalyze;
    }

    public void setShouldAnalyze(Boolean value){
        shouldAnalyze.setValue(value);
    }
}
