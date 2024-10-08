package com.duyth10.dellhieukieugi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.duyth10.dellhieukieugi.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        handleIntent(intent);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.lavender));

        if (savedInstanceState == null) {
            HomeFragment existingFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (existingFragment == null) {
                HomeFragment homeFragment = new HomeFragment();

                if (intent != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("qrData", intent.getStringExtra("qrData"));
                    bundle.putString("amountEntry", intent.getStringExtra("textFromMain"));
                    homeFragment.setArguments(bundle);
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, homeFragment);
                transaction.commit();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    void handleIntent(Intent intent) {
        if (intent != null) {
            String qrData = intent.getStringExtra("qrData");
            String textFromMain = intent.getStringExtra("textFromMain");

            Log.d(TAG, "Received qrData: " + qrData);
            Log.d(TAG, "Received textFromMain: " + textFromMain);

            if (qrData != null && textFromMain != null) {
                sendToFragment(qrData, textFromMain);
            } else {
                Log.d(TAG, "Intent received but no data found.");
            }
        }
    }

    void sendToFragment(String qrData, String textFromMain) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof HomeFragment) {
            HomeFragment existingFragment = (HomeFragment) currentFragment;
            existingFragment.updateData(qrData, textFromMain);
        } else {
            HomeFragment homeFragment = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("qrData", qrData);
            bundle.putString("amountEntry", textFromMain);
            homeFragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, homeFragment);
            transaction.commit();
        }
    }
}
