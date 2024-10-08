package com.duyth10.dellhieukieugi;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.duyth10.dellhieukieugi.R;
import com.duyth10.dellhieukieugi.ui.AmountEntryFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.SystemClock;

@RunWith(AndroidJUnit4.class)
public class AmountEntryFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    private FragmentScenario<AmountEntryFragment> fragmentScenario;

    @Before
    public void setUp() {

        fragmentScenario = FragmentScenario.launchInContainer(AmountEntryFragment.class, (Bundle) null, R.style.AppTheme, (FragmentFactory) null);
    }

    @Test
    public void testInitialDisplayText_isZero() {

        onView(withId(R.id.tv_display))
                .check(matches(withText("0")));
    }

    @Test
    public void testNumberButtonClicked_updatesDisplayText() {

        onView(withId(R.id.btn_5))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.btn_5)).perform(click());

        onView(withId(R.id.tv_display))
                .check(matches(withText("5")));
    }

    @Test
    public void testClearButtonClicked_resetsDisplayTextToZero() {

        onView(withId(R.id.btn_5))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.btn_clean))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.btn_5)).perform(click());
        onView(withId(R.id.btn_5)).perform(click());
        onView(withId(R.id.btn_5)).perform(click());

        onView(withId(R.id.btn_clean)).perform(click());

        onView(withId(R.id.tv_display))
                .check(matches(withText("0")));
    }

    @Test
    public void testDelButtonClicked_deleteLastNumber() {

        onView(withId(R.id.btn_5))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.btn_delete))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));


        onView(withId(R.id.btn_5)).perform(click());
        onView(withId(R.id.btn_5)).perform(click());
        onView(withId(R.id.btn_5)).perform(click());

        onView(withId(R.id.btn_delete)).perform(click());

        onView(withId(R.id.tv_display))
                .check(matches(withText("55")));
    }

//    @Test
//    public void testConfirmButton_showsToastOnZeroAmount() {
//        onView(withId(R.id.btn_confirm))
//                .check(matches(isDisplayed()))
//                .check(matches(isEnabled()));
//
//        onView(withId(R.id.btn_confirm)).perform(click());
//
//        SystemClock.sleep(2000);

//        onView(withText("Số tiền không thể bằng 0!".trim()))
//                .inRoot(new ToastMatcher()) // ToastMatcher là một matcher tùy chỉnh để kiểm tra Toast
//                .check(matches(ViewMatchers.isDisplayed()));
//    }

    @Test
    public void testBackIcon_showBackView() {

        onView(withId(R.id.btn_back))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.btn_back)).perform(click());

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.textHome))
                .check(matches(withText("Home")))
                .check(matches(isDisplayed()));
    }
//
//    @Test
//    public void testConfirmButton_laucherNewActivity(){
//        Intents.init();
//
//        onView(withId(R.id.btn_5))
//                .check(matches(isDisplayed()))
//                .check(matches(isEnabled()));
//
//        onView(withId(R.id.btn_confirm))
//                .check(matches(isDisplayed()))
//                .check(matches(isEnabled()));
//
//        onView(withId(R.id.btn_5)).perform(click());
//        onView(withId(R.id.btn_5)).perform(click());
//        onView(withId(R.id.btn_5)).perform(click());
//
//        onView(withId(R.id.btn_confirm)).perform(click());
//
//        intended(IntentMatchers.hasComponent(new ComponentName("com.duyth10.dellhieukieugiservice",
//                "com.duyth10.dellhieukieugiservice.ui.MainActivity")));
//        intended(IntentMatchers.hasExtra("data", "555"));
//        intended(IntentMatchers.hasExtra("statusBarColor", 0xFF8692F7));
//        intended(IntentMatchers.hasExtra("toolbarColor", 0xFF8692F7));
//
//        Intents.release();
//    }

}
