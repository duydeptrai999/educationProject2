package com.duyth10.dellhieukieugi.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.duyth10.dellhieukieugi.R;

public class HomeViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> _showDialog = new MutableLiveData<>();
    public LiveData<Boolean> showDialog = _showDialog;

    private final MutableLiveData<Boolean> _isDrawerOpen = new MutableLiveData<>();
    public LiveData<Boolean> isDrawerOpen = _isDrawerOpen;

    public void onMenuIconClicked() {
        _isDrawerOpen.setValue(true);
    }

    // Phương thức để đóng DrawerLayout
    public void onDrawerClosed() {
        _isDrawerOpen.setValue(false);
    }


    public void onMenuItemSelected(int itemId) {
        if (itemId == R.id.nav_credit || itemId == R.id.nav_emoney) {
            //   setMessage("Feature not yet integrated");
        } else if (itemId == R.id.nav_qr) {
            //    setMessage("QR selected");
        }
    }

    public void onCreditCardClicked() {
        _showDialog.setValue(true);
    }

    public void resetDialogState() {
        _showDialog.setValue(false);
    }
}
