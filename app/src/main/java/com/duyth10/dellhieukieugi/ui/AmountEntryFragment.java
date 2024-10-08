package com.duyth10.dellhieukieugi.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.duyth10.dellhieukieugi.R;
import com.duyth10.dellhieukieugi.databinding.FragmentAmountEntryBinding;
import com.duyth10.dellhieukieugi.viewmodel.AmountEntryViewModel;
import com.duyth10.dellhieukieugi.viewmodel.ColorViewModel;

public class AmountEntryFragment extends Fragment {

    private AmountEntryViewModel amountViewModel;
    private ColorViewModel colorViewModel;
    private FragmentAmountEntryBinding binding;

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
        btnConfirm.setOnClickListener(v -> {
            if (!amountViewModel.getDisplayText().getValue().equals("0")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.duyth10.dellhieukieugiservice", "com.duyth10.dellhieukieugiservice.ui.MainActivity"));
                intent.putExtra("data", amountViewModel.getDisplayText().getValue());


                intent.putExtra("statusBarColor", colorViewModel.getStatusBarColor().getValue());
                intent.putExtra("toolbarColor", colorViewModel.getTabLayoutColor().getValue());

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                requireActivity().startActivity(intent);

            } else {
                Toast.makeText(getContext(), "Số tiền không thể bằng 0!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
