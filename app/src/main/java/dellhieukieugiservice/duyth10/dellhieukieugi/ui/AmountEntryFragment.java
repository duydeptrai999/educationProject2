package dellhieukieugiservice.duyth10.dellhieukieugi.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.duyth10.dellhieukieugi.R;
import com.duyth10.dellhieukieugi.databinding.FragmentAmountEntryBinding;
import com.duyth10.dellhieukieugiservice.IMyAidlInterface;

import dellhieukieugiservice.duyth10.dellhieukieugi.viewmodel.AmountEntryViewModel;
import dellhieukieugiservice.duyth10.dellhieukieugi.viewmodel.ColorViewModel;

public class AmountEntryFragment extends Fragment {

    private AmountEntryViewModel amountViewModel;
    private ColorViewModel colorViewModel;
    private FragmentAmountEntryBinding binding;

    private IMyAidlInterface myAidlService;
    private boolean isBound = false;

    // call back quan ly ket noi
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlService = IMyAidlInterface.Stub.asInterface(service);
            isBound = true;
            Log.d("AmountEntryFragment", "Service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myAidlService = null;
            isBound = false;
            Log.d("AmountEntryFragment", "Service disconnected");
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = new Intent(IMyAidlInterface.class.getName());
        intent.setPackage("com.duyth10.dellhieukieugiservice");
        intent.setComponent(new ComponentName(
                "com.duyth10.dellhieukieugiservice",
                "com.duyth10.dellhieukieugiservice.service.DataProcessingService"
        ));

        try {
            boolean success = requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            if (!success) {
                Log.e("AmountEntryFragment", "Failed to bind to service");
            } else {
                Log.d("AmountEntryFragment", "Binding initiated successfully");
            }
        } catch (Exception e) {
            Log.e("AmountEntryFragment", "Error binding service", e);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (isBound) {
            requireActivity().unbindService(serviceConnection);
            isBound = false;
        }
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_amount_entry, container, false);
        View view = binding.getRoot();


        amountViewModel = new ViewModelProvider(this).get(AmountEntryViewModel.class);
        colorViewModel = new ViewModelProvider(requireActivity()).get(ColorViewModel.class);


        binding.setAmountViewModel(amountViewModel);
        binding.setColorViewModel(colorViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        Button btnConfirm = view.findViewById(R.id.btn_confirm);
//        btnConfirm.setOnClickListener(v -> {
//            if (!amountViewModel.getDisplayText().getValue().equals("0")) {
//
////                for(int i = 0 ; i<100; i++) {
//                    Intent intent = new Intent();
//                    intent.setComponent(new ComponentName("com.duyth10.dellhieukieugiservice", "com.duyth10.dellhieukieugiservice.ui.MainActivity"));
//                    intent.putExtra("data", amountViewModel.getDisplayText().getValue());
//
//                    Log.d("AmountEntryFrg", "" + amountViewModel.getDisplayText().getValue());
//
//                    intent.putExtra("statusBarColor", colorViewModel.getStatusBarColor().getValue());
//                    intent.putExtra("toolbarColor", colorViewModel.getTabLayoutColor().getValue());
//
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//
//                    requireActivity().startActivity(intent);
//              // }
//
//            } else {
//                Toast.makeText(getContext(), "Số tiền không thể bằng 0!", Toast.LENGTH_SHORT).show();
//            }
//        });

        btnConfirm.setOnClickListener(v -> {
            if (!amountViewModel.getDisplayText().getValue().equals("0")) {
                if (isBound && myAidlService != null) {
                    try {
                        String data = amountViewModel.getDisplayText().getValue();
                        myAidlService.sendData(data);
                        Log.d("AmountEntryFragment", "Successfully sent data: " + data);

                        launchServiceApp(data);
                    } catch (RemoteException e) {
                        Log.e("AmountEntryFragment", "RemoteException when sending data", e);
                    }
                } else {
                    Log.e("AmountEntryFragment", "Service not bound or interface is null");
                }
            } else {
                Toast.makeText(getContext(), "Số tiền không thể bằng 0!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void launchServiceApp(String data) {
        Intent launchIntent = new Intent();
        launchIntent.setComponent(new ComponentName(
                "com.duyth10.dellhieukieugiservice",
                "com.duyth10.dellhieukieugiservice.ui.MainActivity"
        ));
        launchIntent.putExtra("data", data);
        launchIntent.putExtra("statusBarColor", colorViewModel.getStatusBarColor().getValue());
        launchIntent.putExtra("toolbarColor", colorViewModel.getTabLayoutColor().getValue());
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(launchIntent);
        } catch (Exception e) {
            Log.e("AmountEntryFragment", "Error launching service app", e);
            Toast.makeText(getContext(), "Không thể mở ứng dụng đích", Toast.LENGTH_SHORT).show();
        }
    }
}
