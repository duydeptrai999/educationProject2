package dellhieukieugiservice.duyth10.dellhieukieugi.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter("android:background")
    public static void setToolbarBackgroundColor(Toolbar toolbar, Integer color) {
        if (color != null) {
            toolbar.setBackgroundColor(color);
        }
    }

}