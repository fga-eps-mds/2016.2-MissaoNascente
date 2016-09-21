package com.example.jbbmobile;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;

import com.example.jbbmobile.view.PreferenceScreenActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.endsWith;

@RunWith(AndroidJUnit4.class)

@LargeTest
public class PreferenceControllerAcceptanceTest {
    @Rule public final ActivityTestRule<PreferenceScreenActivity> login = new ActivityTestRule<>(PreferenceScreenActivity.class);

    @Test
    public void logUserOut(){
        onView(withId(2131427443))
                .perform(click());
    }

    @Test
    public void editUser(){
        onView(withId(2131427439))
                .perform(click());
        onView(withClassName(endsWith("EditText"))).perform(typeText("novonick"));
    }
    
}

