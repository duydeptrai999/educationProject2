package com.duyth10.dellhieukieugi;

import static junit.framework.TestCase.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import dellhieukieugiservice.duyth10.dellhieukieugi.model.AmountEntryModel;
import dellhieukieugiservice.duyth10.dellhieukieugi.viewmodel.AmountEntryViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AmountEntryViewModelTest {
    private AmountEntryViewModel amountEntryViewModel;

    private AmountEntryModel amountEntryModel;

    @Mock
    private Observer<String> stringObserver;

    private AutoCloseable mocks;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        amountEntryModel = Mockito.mock(AmountEntryModel.class);
        amountEntryViewModel = new AmountEntryViewModel();
        amountEntryViewModel.getDisplayText().observeForever(stringObserver);

    }
    @After
    public void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    public void AmountEntryViewModel_shouldUpdateTextDisplay_whenGetTextDisplayIsCall() {

        Mockito.when(amountEntryModel.formatNumber()).thenReturn("0");

        assertEquals("0", amountEntryViewModel.getDisplayText().getValue());


    }

    @Test
    public void amountEntryViewModel_shouldClearDisplayText_whenCleanButtonClicked() {

        amountEntryViewModel.onNumberButtonClicked("clean");

        // Sử dụng ArgumentCaptor để bắt các giá trị được gửi đến observer
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(stringObserver, times(2)).onChanged(captor.capture());

        assertEquals("0", captor.getValue());
        assertEquals("0", amountEntryViewModel.getDisplayText().getValue());

    }

    @Test
    public void amountEntryViewModel_shouldDeleteLastCharacter_whenDeleteButtonClicked() {

        amountEntryViewModel.onNumberButtonClicked("9");
        amountEntryViewModel.onNumberButtonClicked("9");
        amountEntryViewModel.onNumberButtonClicked("9");

        verify(stringObserver).onChanged("999");

        amountEntryViewModel.onNumberButtonClicked("del");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);


        verify(stringObserver, times(5)).onChanged(captor.capture());
        assertEquals("99",amountEntryViewModel.getDisplayText().getValue());


    }

    @Test
    public void amountEntryViewModel_shouldAppendNumber_whenNumberButtonClicked() {
        amountEntryViewModel.onNumberButtonClicked("9");

        verify(stringObserver).onChanged("9");

    }

}
