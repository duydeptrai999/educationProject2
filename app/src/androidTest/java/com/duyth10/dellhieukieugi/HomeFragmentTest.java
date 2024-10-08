package com.duyth10.dellhieukieugi;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.NoActivityResumedException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import com.duyth10.dellhieukieugi.R;
import com.duyth10.dellhieukieugi.ui.HomeFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertThrows;

import java.io.IOException;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeFragmentTest {

    private FragmentScenario<HomeFragment> fragmentScenario;

    @Before
    public void setup() throws IOException {
        fragmentScenario = FragmentScenario.launchInContainer(HomeFragment.class, null , R.style.AppTheme);

        fragmentScenario.onFragment(fragment -> {
        });
    }

    @After
    public void tearDown() throws Exception {
        if (fragmentScenario != null) {
            fragmentScenario.close();
        }
    }

    @Test
    public void testDefaultUI() {

        onView(withId(R.id.text1))
                .check(matches(withText("what u know")))
                .check(matches(isDisplayed()));

        onView(withId(R.id.img1))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testClickImgCreditShowsDialog() {

        //        onView(withId(R.id.imgCredit))
//                .check(matches(isDisplayed()))  // Check visibility
//                .check(matches(isEnabled()))  // Check if clickable
//                .perform(click());  // Perform the click action

        fragmentScenario.onFragment(fragment -> {
            ImageView img = fragment.getView().findViewById(R.id.imgCredit);
            img.performClick();
        });

        onView(withText("Feature not yet integrated"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        // Đóng Dialog
        onView(withText("OK")).perform(click());
    }

    @Test
    public void testClickImgMoneyShowsDialog() {
        onView(withId(R.id.imgMoney))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.imgMoney)).perform(click());

        onView(withText("Feature not yet integrated"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        onView(withText("OK")).perform(click());
    }

    @Test
    public void testClickImgQRNavigatesToAmountEntryFragment() {

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.imgQR))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.imgQR)).perform(click());

        onView(withId(R.id.textAmountEnt))
                .check(matches(withText("Amount Ent")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationDrawerOpensAndItemClicks() {

        onView(withId(R.id.custom_menu_icon))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withId(R.id.custom_menu_icon)).perform(click());
        onView(withId(R.id.nav_view))
                .check(matches(isDisplayed()));

        onView(withText("credit")).perform(click());

        // Kiểm tra Dialog hiển thị
        onView(withText("Feature not yet integrated"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        // Đóng Dialog
        onView(withText("OK")).perform(click());
    }

    @Test
    public void testDialogAfterTransaction() {
        fragmentScenario.onFragment(fragment -> {
            Bundle args = new Bundle();
            args.putString("qrData", "SampleQRData");
            args.putString("amountEntry", "100");
            fragment.setArguments(args);
        });

        fragmentScenario.recreate();

        onView(withText("Printing SampleQRData complete transaction 100"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Do you want to print the bill?"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        onView(withText("OK")).perform(click());
    }
}
