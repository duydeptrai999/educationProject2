    package com.duyth10.dellhieukieugi.ui;
    import android.content.ComponentName;
    import android.content.Intent;
    import android.os.Bundle;
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.ViewModelProvider;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.duyth10.dellhieukieugi.MainActivity;
    import com.duyth10.dellhieukieugi.R;
    import com.duyth10.dellhieukieugi.viewmodel.AmountEntryViewModel;

    public class AmountEntryFragment extends Fragment {

        private TextView tvDisplay;
        private AmountEntryViewModel viewModel;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_amount_entry, container, false);
            tvDisplay = view.findViewById(R.id.tv_display);
            viewModel = new ViewModelProvider(this).get(AmountEntryViewModel.class);

            viewModel.getDisplayText().observe(getViewLifecycleOwner(), display -> {
                tvDisplay.setText(display);
            });

            // Khởi tạo các nút số
            int[] buttonIds = new int[]{
                    R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                    R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
                    R.id.btn_delete, R.id.btn_clean
            };

            for (int id : buttonIds) {
                Button button = view.findViewById(id);
                button.setOnClickListener(this::onNumberButtonClicked);
            }

            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ImageButton btnBack = view.findViewById(R.id.btn_back);
            btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

            Button btnConfirm = view.findViewById(R.id.btn_confirm);
            btnConfirm.setOnClickListener(v -> {
                if (!tvDisplay.getText().toString().equals("0")) {
                    // Xử lý logic khi người dùng nhấn nút xác nhận
                    Log.d("app1","11111");
//                    Intent intent = new Intent();
//                    intent.setComponent(new ComponentName("com.duyth10.dellhieukieugiservice", "com.duyth10.dellhieukieugiservice.DataProcessingService"));
//                    intent.putExtra("data", tvDisplay.getText().toString());

                    Intent intent1 = new Intent();
                    intent1.setComponent(new ComponentName("com.duyth10.dellhieukieugiservice", "com.duyth10.dellhieukieugiservice.ui.MainActivity"));
                    intent1.putExtra("data", tvDisplay.getText().toString());


                    requireActivity().startActivity(intent1);


//                    requireActivity().startService(intent);




                } else {
                    Toast.makeText(getContext(), "Số tiền không thể bằng 0!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void onNumberButtonClicked(View view) {
            Button button = (Button) view;
            viewModel.onNumberButtonClicked(button.getText().toString());
        }
    }
