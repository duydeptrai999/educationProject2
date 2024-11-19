package dellhieukieugiservice.duyth10.dellhieukieugi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.duyth10.dellhieukieugi.R;

public class ColorViewModel extends ViewModel {
    private final MutableLiveData<Integer> statusBarColor = new MutableLiveData<>(0xFF8692F7);
    private final MutableLiveData<Integer> tabLayoutColor = new MutableLiveData<>(0xFF8692F7);
    private final MutableLiveData<Integer> imgView = new MutableLiveData<>(R.drawable.coin);
    private final MutableLiveData<String> txView = new MutableLiveData<>("what u know");


    public ColorViewModel() {
        imgView.setValue(R.drawable.coin);
        txView.setValue("what u know");
    }

    public LiveData<Integer> getStatusBarColor() {
        return statusBarColor;
    }

    public LiveData<Integer> getTabLayoutColor() {
        return tabLayoutColor;
    }

    public LiveData<Integer> getImgView() {
        return imgView;
    }


    public LiveData<String> getTextView() {
        return txView;
    }

    public void setImgView(int resId) {
        imgView.setValue(resId);
    }

    public void setTextView(String text) {
        txView.setValue(text);
    }

    public void setColors(int statusBarColor, int tabLayoutColor) {
        this.statusBarColor.setValue(statusBarColor);
        this.tabLayoutColor.setValue(tabLayoutColor);
    }
}

