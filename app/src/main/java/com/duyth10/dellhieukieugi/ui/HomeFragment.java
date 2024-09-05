package com.duyth10.dellhieukieugi.ui;

import static android.content.Intent.getIntent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.duyth10.dellhieukieugi.R;
import com.duyth10.dellhieukieugi.viewmodel.HomeViewModel;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends BaseFragment {
    private DrawerLayout drawerLayout;
    private HomeViewModel homeViewModel;

    private String qrData;
    private String amountEntry;


    private View dialogView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        dialogView = inflater.inflate(R.layout.progress_dialog, null);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imgQR = view.findViewById(R.id.imgQR);
        ImageView imgMoney = view.findViewById(R.id.imgMoney);
        ImageView imgCredit = view.findViewById(R.id.imgCredit);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item -> {
            homeViewModel.onMenuItemSelected(item.getItemId());
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });

        homeViewModel.getMessage().observe(getViewLifecycleOwner(), this::showMessage);

        view.findViewById(R.id.custom_menu_icon).setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        imgCredit.setOnClickListener(v -> showDialog("Feature not yet integrated"));
        imgMoney.setOnClickListener(v -> showDialog("Feature not yet integrated"));
        imgQR.setOnClickListener(v -> navigateToNextFragment());


        Bundle args = getArguments();
        if (args != null) {
            qrData = getArguments().getString("qrData");
            amountEntry = getArguments().getString("amountEntry");

            if (qrData != null || amountEntry != null) {


                dialogCompleteTransaction(qrData, amountEntry);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialog("Do you want to print the bill?");
                    }
                }, 5000);
            }
        }

        Log.d("HomeFragment", "Received qrData: " + qrData);
        Log.d("HomeFragment", "Received textFromMain: " + amountEntry);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Warning").setMessage(message).setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dialogCompleteTransaction(String qrData, String amountEntry) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());


        builder1.setTitle("Notification").setTitle("printing " + qrData + " complete transaction " + amountEntry).setView(dialogView).setCancelable(false);


        AlertDialog progressDialog = builder1.create();


        progressDialog.show();

        // Close the dialog after 5 seconds


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, 5000);
    }

    private void navigateToNextFragment() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new AmountEntryFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showMessage(String message) {
        if ("QR selected".equals(message)) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            showDialog(message);
        }
    }

    public void updateData(String qrData, String amountEntry) {
        this.qrData = qrData;
        this.amountEntry = amountEntry;

    }
}
