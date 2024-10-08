package com.duyth10.dellhieukieugi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    protected final MutableLiveData<String> message = new MutableLiveData<>();


    public LiveData<String> getMessage() {
        return message;
    }


    protected void setMessage(String msg) {
        message.setValue(msg);
    }
}
