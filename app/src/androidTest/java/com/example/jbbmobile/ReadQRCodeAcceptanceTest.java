package com.example.jbbmobile;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import static android.support.test.espresso.Espresso.onView;

import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;
import com.example.jbbmobile.view.LoginScreenActivity;
import com.example.jbbmobile.view.MainScreenActivity;
import com.example.jbbmobile.view.RegisterScreenActivity;
import com.example.jbbmobile.view.StartScreenActivity;

import java.io.IOException;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)

public class ReadQRCodeAcceptanceTest {
    @Rule public ActivityTestRule<StartScreenActivity> start = new ActivityTestRule<>(StartScreenActivity.class);

    @Test
    public void registerUser(){
        onView(withId(R.id.createAccountRelativeLayout))
                .perform(click());
        onView(withId(R.id.nicknameEditText))
                .perform(typeText("testuser"));
        onView(withId(R.id.passwordEditText))
                .perform(typeText("senha1234"));
        onView(withId(R.id.passwordConfirmEditText))
                .perform(typeText("senha1234"))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.emailEditText))
                .perform(typeText("testuser@gmail.com"))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.registerButton))
                .perform(click());
        onView(withId(R.id.readQrCodeButton))
                .perform(click());
    }
}

