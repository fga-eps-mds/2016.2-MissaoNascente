package com.example.jbbmobile;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;
import com.example.jbbmobile.view.LoginScreenActivity;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.view.RegisterScreenActivity;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

@LargeTest
public class LogInAcceptanceTest {
    @Rule public final ActivityTestRule<LoginScreenActivity> login = new ActivityTestRule<>(LoginScreenActivity.class);



    @LargeTest
    public class ExplorerAcceptanceTest {
        @Rule
        public final ActivityTestRule<RegisterScreenActivity> register = new ActivityTestRule<>(RegisterScreenActivity.class);

        @Test
        public void registerUser() {
            onView(withId(R.id.nicknameEditText))
                    .perform(typeText("user"));
            onView(withId(R.id.passwordEditText))
                    .perform(typeText("12345678"));
            onView(withId(R.id.passwordConfirmEditText))
                    .perform(typeText("12345678"))
                    .perform(closeSoftKeyboard());
            onView(withId(R.id.emailEditText))
                    .perform(typeText("user@email.com"))
                    .perform(closeSoftKeyboard());
            onView(withId(R.id.registerButton))
                    .perform(click());
        }
    }

    @Test
    public void logUser(){
        onView(withId(R.id.emailEditText))
                .perform(typeText("user@email.com"));
        onView(withId(R.id.passwordEditText))
                .perform(typeText("12345678"))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton))
                .perform(click());
    }
}

