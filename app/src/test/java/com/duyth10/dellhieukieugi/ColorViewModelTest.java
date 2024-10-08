package com.duyth10.dellhieukieugi;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import com.duyth10.dellhieukieugi.viewmodel.ColorViewModel;
import com.duyth10.dellhieukieugi.R;

public class ColorViewModelTest {

    private ColorViewModel colorViewModel;

    @Mock
    private Observer<Integer> integerObserver;

    @Mock
    private Observer<String> stringObserver;

    private AutoCloseable mocks;

    // InstantTaskExecutorRule cho phép LiveData chạy ngay lập tức trên luồng hiện tại
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        // Khởi tạo ViewModel và mock các Observer
        mocks = MockitoAnnotations.openMocks(this);
        colorViewModel = new ColorViewModel();
    }

    @After
    public void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    public void colorViewModel_shouldReturnInitialValues_whenViewModelIsCreated() {
        // Kiểm tra giá trị ban đầu của imgView và txView
        assertEquals(Integer.valueOf(R.drawable.coin), colorViewModel.getImgView().getValue());
        assertEquals("what u know", colorViewModel.getTextView().getValue());
    }

    @Test
    public void colorViewModel_shouldUpdateImageView_whenSetImgViewIsCalled() {
        // Quan sát thay đổi của imgView
        colorViewModel.getImgView().observeForever(integerObserver);

        // Gọi phương thức setImgView và kiểm tra kết quả
        colorViewModel.setImgView(R.drawable.money);
        verify(integerObserver).onChanged(R.drawable.money);  // Xác minh xem observer có nhận đúng giá trị không
        assertEquals(Integer.valueOf(R.drawable.money), colorViewModel.getImgView().getValue());
    }

    @Test
    public void colorViewModel_shouldUpdateTextView_whenSetTextViewIsCalled() {
        // Quan sát thay đổi của txView
        colorViewModel.getTextView().observeForever(stringObserver);

        // Gọi phương thức setTextView và kiểm tra kết quả
        String newText = "new text";
        colorViewModel.setTextView(newText);
        verify(stringObserver).onChanged(newText);  // Xác minh xem observer có nhận đúng giá trị không
        assertEquals(newText, colorViewModel.getTextView().getValue());
    }

    @Test
    public void colorViewModel_shouldUpdateColors_whenSetColorsIsCalled() {
        // Quan sát thay đổi của statusBarColor và tabLayoutColor
        colorViewModel.getStatusBarColor().observeForever(integerObserver);
        colorViewModel.getTabLayoutColor().observeForever(integerObserver);

        // Gọi phương thức setColors và kiểm tra kết quả
        int newStatusBarColor = 0xFF000000;
        int newTabLayoutColor = 0xFFFFFFFF;
        colorViewModel.setColors(newStatusBarColor, newTabLayoutColor);

        verify(integerObserver).onChanged(newStatusBarColor);  // Xác minh xem observer có nhận đúng giá trị không
        verify(integerObserver).onChanged(newTabLayoutColor);  // Xác minh xem observer có nhận đúng giá trị không
        assertEquals(Integer.valueOf(newStatusBarColor), colorViewModel.getStatusBarColor().getValue());
        assertEquals(Integer.valueOf(newTabLayoutColor), colorViewModel.getTabLayoutColor().getValue());
    }
}
