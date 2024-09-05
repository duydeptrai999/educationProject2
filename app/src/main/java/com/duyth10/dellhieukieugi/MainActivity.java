package com.duyth10.dellhieukieugi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.duyth10.dellhieukieugi.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        handleIntent(intent);

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

                // Bắt đầu một FragmentTransaction
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Gán Fragment vào FrameLayout với ID fragment_container
                transaction.add(R.id.fragment_container, homeFragment);

                // Commit transaction
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

    private void handleIntent(Intent intent) {
        if (intent != null) {
            // Lấy dữ liệu từ Intent
            String qrData = intent.getStringExtra("qrData");
            String textFromMain = intent.getStringExtra("textFromMain");

            Log.d("MainActivityyyy", "Received qrData: " + qrData);
            Log.d("MainActivityyyy", "Received textFromMain: " + textFromMain);

            // Kiểm tra và xử lý dữ liệu nếu không null
            if (qrData != null && textFromMain != null) {
                // Ví dụ: Hiển thị dữ liệu lên màn hình hoặc xử lý logic
                Log.d("MainActivityyyy", "Received qrData: " + qrData);
                Log.d("MainActivityyyy", "Received textFromMain: " + textFromMain);
                sendToFragment(qrData, textFromMain);
            } else {
                Log.d("MainActivityyyy", "Intent received but no data found.");
            }
        }
    }

    private void sendToFragment(String qrData, String textFromMain) {

        HomeFragment existingFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);


        if(existingFragment != null){
            existingFragment.updateData(qrData, textFromMain);
        }

        // Tạo một Fragment mới
        HomeFragment homeFragment = new HomeFragment();

        Bundle bundle = new Bundle();
        bundle.putString("qrData", qrData);
        bundle.putString("amountEntry", textFromMain);
        homeFragment.setArguments(bundle);

        // Bắt đầu một FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Gán Fragment vào FrameLayout với ID fragment_container
        transaction.replace(R.id.fragment_container, homeFragment);

        // Commit transaction
        transaction.commit();
    }


}