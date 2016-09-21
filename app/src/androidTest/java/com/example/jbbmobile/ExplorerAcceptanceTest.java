package com.example.jbbmobile;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;

import com.example.jbbmobile.controller.Register;
import com.example.jbbmobile.view.RegisterScreenActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

@LargeTest
public class ExplorerAcceptanceTest{
    @Rule public final ActivityTestRule<RegisterScreenActivity> register = new ActivityTestRule<>(RegisterScreenActivity.class);

    @Test
    public void registerUser(){
        onView(withId(2131427442))
                .perform(typeText("testuser"));
        onView(withId(2131427427))
                .perform(typeText("senha1234"));
        onView(withId(2131427443))
                .perform(typeText("senha1234"));
        onView(withId(2131427426))
                .perform(typeText("testuser@gmail.com"))
                .perform(closeSoftKeyboard());
        onView(withId(2131427444))
                .perform(click())
                .check(matches(isDisplayed()));
    }
}