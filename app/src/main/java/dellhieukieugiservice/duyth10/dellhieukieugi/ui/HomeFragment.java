package dellhieukieugiservice.duyth10.dellhieukieugi.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.duyth10.dellhieukieugi.R;
import com.duyth10.dellhieukieugi.databinding.FragmentHomeBinding;
import dellhieukieugiservice.duyth10.dellhieukieugi.viewmodel.ColorViewModel;
import dellhieukieugiservice.duyth10.dellhieukieugi.viewmodel.HomeViewModel;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends BaseFragment {

    private DrawerLayout drawerLayout;
    public HomeViewModel homeViewModel;
    public ColorViewModel colorViewModel;
    private String qrData;
    private String amountEntry;
    private View dialogView;
    private TextView text1;
    private ImageView img1;
    private boolean isDialogShown = false;
    private boolean hasShownTransactionDialog = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);


        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        colorViewModel = new ViewModelProvider(requireActivity()).get(ColorViewModel.class);


        binding.setColorViewModel(colorViewModel);
        binding.setLifecycleOwner(this);
        binding.setHomeViewModel(homeViewModel);


        dialogView = inflater.inflate(R.layout.progress_dialog, null);


        // Theo dõi trạng thái LiveData của Drawer và đóng nếu cần thiết


        homeViewModel.showDialog.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldShow) {
                if (shouldShow) {
                    // Hiển thị dialog khi người dùng click vào Credit
                    handleImageViewClick("Feature not yet integrated");
                    // Reset lại trạng thái để tránh hiển thị dialog nhiều lần
                    homeViewModel.resetDialogState();
                }
            }
        });


        // Theo dõi trạng thái LiveData của Drawer và đóng nếu cần thiết
        homeViewModel.isDrawerOpen.observe(getViewLifecycleOwner(), isOpen -> {
            if (!isOpen && drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgQR = view.findViewById(R.id.imgQR);
        ImageView imgMoney = view.findViewById(R.id.imgMoney);
        ImageView imgCredit = view.findViewById(R.id.imgCredit);
        img1 = view.findViewById(R.id.img1);
        text1 = view.findViewById(R.id.text1);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);


        img1.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.coin));


        text1.setText("Default Text");


        homeViewModel.isDrawerOpen.observe(getViewLifecycleOwner(), isOpen -> {
            if (isOpen) {
                drawerLayout.openDrawer(GravityCompat.END);
            } else if (!isOpen && drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        colorViewModel.getStatusBarColor().observe(getViewLifecycleOwner(), color -> {
            if (getActivity() != null) {
                getActivity().getWindow().setStatusBarColor(color);
            }
        });

        colorViewModel.getTabLayoutColor().observe(getViewLifecycleOwner(), color -> {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setBackgroundColor(color);
            }
        });


        homeViewModel.showDialog.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldShow) {
                if (shouldShow) {

                    handleImageViewClick("Feature not yet integrated");
                    // Reset lại trạng thái để tránh hiển thị dialog nhiều lần
                    homeViewModel.resetDialogState();
                }
            }
        });


        NavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.END);
            handleNavigationItem(item.getItemId());

            if (item.getItemId() == R.id.nav_credit) {
                colorViewModel.setImgView(R.drawable.creditcard);
                colorViewModel.setTextView("Credit Selected");
                handleImageViewClick("Feature not yet integrated");
            } else if (item.getItemId() == R.id.nav_emoney) {
                colorViewModel.setImgView(R.drawable.money);
                colorViewModel.setTextView("eMoney Selected");
                handleImageViewClick("Feature not yet integrated");
            } else if (item.getItemId() == R.id.nav_qr) {
                colorViewModel.setImgView(R.drawable.qr);
                colorViewModel.setTextView("QR Selected");
                navigateToNextFragment();
            }
            return true;
        });


        view.findViewById(R.id.custom_menu_icon).setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        imgCredit.setOnClickListener(v -> handleImageViewClick("Feature not yet integrated"));
        imgMoney.setOnClickListener(v -> handleImageViewClick("Feature not yet integrated"));
        imgQR.setOnClickListener(v -> navigateToNextFragment());


        Bundle args = getArguments();
        if (args != null) {
            qrData = args.getString("qrData");
            amountEntry = args.getString("amountEntry");

            if (qrData != null || amountEntry != null) {
                dialogCompleteTransaction(qrData, amountEntry);
                new Handler().postDelayed(() -> showDialog("Do you want to print the bill?"), 5000);
                hasShownTransactionDialog = true;

                args.clear();
            }
        }

        Log.d("HomeFragment", "Received qrData: " + qrData);
        Log.d("HomeFragment", "Received amountEntry: " + amountEntry);
    }

    @Override
    public void onResume() {
        super.onResume();
        isDialogShown = false;
    }


    public void showDialog(String message) {
        if (!isDialogShown) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog);//djtme lỗi l khi chạy test thì sẽ bị lỗi  , do mở rộng cái appcompatialog (mặc dù set đúng trong manifest và main rồi) gì đó ko phi theme của app compat nên lỗi nên phải truyền thêm style vào hết lôix
            builder.setTitle("Warning").setMessage(message).setPositiveButton("OK", (dialog, which) -> {
                dialog.dismiss();
                isDialogShown = false;
            }).setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
                isDialogShown = false;
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            isDialogShown = true;
        }
    }

    public void handleImageViewClick(String message) {
        showDialog(message);
    }

    private void dialogCompleteTransaction(String qrData, String amountEntry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Notification").setMessage("Printing " + qrData + " complete transaction " + amountEntry).setView(dialogView).setCancelable(false);
        AlertDialog progressDialog = builder.create();
        progressDialog.show();

        new Handler().postDelayed(() -> {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }, 5000);
    }

    private void navigateToNextFragment() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new AmountEntryFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void handleNavigationItem(int itemId) {
        if (itemId == R.id.nav_credit) {
            colorViewModel.setColors(ContextCompat.getColor(getContext(), R.color.cherryBlossom), ContextCompat.getColor(getContext(), R.color.cherryBlossom));
        } else if (itemId == R.id.nav_emoney) {
            colorViewModel.setColors(ContextCompat.getColor(getContext(), R.color.lime), ContextCompat.getColor(getContext(), R.color.lime));
        }
    }

    public void updateData(String qrData, String amountEntry) {
        this.qrData = qrData;
        this.amountEntry = amountEntry;

    }
}
