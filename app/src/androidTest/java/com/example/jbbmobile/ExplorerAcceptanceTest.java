package com.example.jbbmobile;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;

import com.example.jbbmobile.view.LoginScreenActivity;
import com.example.jbbmobile.view.PreferenceScreenActivity;
import com.example.jbbmobile.view.RegisterScreenActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

@LargeTest
public class ExplorerAcceptanceTest{
    @Rule
    public final ActivityTestRule<RegisterScreenActivity> register = new ActivityTestRule<>(RegisterScreenActivity.class);


    @Test
    public void registerUser(){
        onView(withId(2131427445))
                .perform(typeText("testuser"));
        onView(withId(2131427430))
                .perform(typeText("senha1234"));
        onView(withId(2131427446))
                .perform(typeText("senha1234"));
        onView(withId(2131427429))
                .perform(typeText("testuser@gmail.com"))
                .perform(closeSoftKeyboard());
        onView(withId(2131427447))
                .perform(click());
    }

    @RunWith(AndroidJUnit4.class)

    @LargeTest
    public class PreferenceAcceptanceTest{
        @Rule public final ActivityTestRule<PreferenceScreenActivity> login = new ActivityTestRule<>(PreferenceScreenActivity.class);

        @Test
        public void logUserOut(){
            onView(withId(2131427443))
                    .perform(click());
        }
    }

    @LargeTest
    public static class LogInAcceptanceTest{
        @Rule public final ActivityTestRule<LoginScreenActivity> login = new ActivityTestRule<>(LoginScreenActivity.class);

        @Test
        public void logUser(){
            onView(withId(2131427429))
                    .perform(typeText("testuser@gmail.com"));
            onView(withId(2131427430))
                    .perform(typeText("senha1234"))
                    .perform(closeSoftKeyboard());
            onView(withId(2131427431))
                    .perform(click());
        }
    }
}