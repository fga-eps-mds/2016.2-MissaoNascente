package com.example.jbbmobile;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;

import com.example.jbbmobile.view.LoginScreenActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

@LargeTest
public class LogInAcceptanceTest {
    @Rule public final ActivityTestRule<LoginScreenActivity> login = new ActivityTestRule<>(LoginScreenActivity.class);

    @Test
    public void logUser(){
        onView(withId(R.id.emailEditText))
                .perform(typeText("testuser@gmail.com"));
        onView(withId(R.id.passwordEditText))
                .perform(typeText("senha1234"))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton))
                .perform(click());
    }
}

