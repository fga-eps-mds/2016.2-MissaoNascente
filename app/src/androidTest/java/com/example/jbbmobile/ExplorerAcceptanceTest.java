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

@RunWith(AndroidJUnit4.class)

@LargeTest
public class ExplorerAcceptanceTest{
    @Rule public final ActivityTestRule<RegisterScreenActivity> register = new ActivityTestRule<>(RegisterScreenActivity.class);

    @Test
    public void emptyTest(){
    }
}