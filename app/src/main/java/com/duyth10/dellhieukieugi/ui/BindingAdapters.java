package com.duyth10.dellhieukieugi.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.BindingAdapter;
import androidx.drawerlayout.widget.DrawerLayout;

public class BindingAdapters {

    @BindingAdapter("android:background")
    public static void setToolbarBackgroundColor(Toolbar toolbar, Integer color) {
        if (color != null) {
            toolbar.setBackgroundColor(color);
        }
    }

}