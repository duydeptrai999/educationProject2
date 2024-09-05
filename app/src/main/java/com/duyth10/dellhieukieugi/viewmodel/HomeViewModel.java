package com.duyth10.dellhieukieugi.viewmodel;

import com.duyth10.dellhieukieugi.R;

public class HomeViewModel extends BaseViewModel {

    public void onMenuItemSelected(int itemId) {
        if (itemId == R.id.nav_credit || itemId == R.id.nav_emoney) {
            setMessage("Feature not yet integrated");
        } else if (itemId == R.id.nav_qr) {
            setMessage("QR selected");
        }
    }
}
