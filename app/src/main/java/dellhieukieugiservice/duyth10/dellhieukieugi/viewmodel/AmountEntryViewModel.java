package dellhieukieugiservice.duyth10.dellhieukieugi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dellhieukieugiservice.duyth10.dellhieukieugi.model.AmountEntryModel;

public class AmountEntryViewModel extends BaseViewModel {
    private final AmountEntryModel model;
    private final MutableLiveData<String> displayText = new MutableLiveData<>();

    public AmountEntryViewModel() {
        model = new AmountEntryModel();
        displayText.setValue(model.formatNumber());
    }

    public LiveData<String> getDisplayText() {
        return displayText;
    }

    public void onNumberButtonClicked(String number) {
        switch (number) {
            case "clean":
                model.clear();
                break;
            case "del":
                model.deleteLast();
                break;
            default:
                model.appendNumber(number);
                break;
        }
        displayText.setValue(model.formatNumber());
    }
}
