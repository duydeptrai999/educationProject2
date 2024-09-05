package com.duyth10.dellhieukieugi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    protected final MutableLiveData<String> message = new MutableLiveData<>();

    // Getter cho message
    public LiveData<String> getMessage() {
        return message;
    }

    // Phương thức tiện ích chung mà cả hai ViewModel có thể dùng
    protected void setMessage(String msg) {
        message.setValue(msg);
    }
}
