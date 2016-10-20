package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.controller.RegisterElementController;
import com.example.jbbmobile.view.StartScreenActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainControllerTest {
    @Rule
    public final ActivityTestRule<StartScreenActivity> startScreen = new ActivityTestRule<>(StartScreenActivity.class) ;

    private Context context;
    private MainController mainController;
    private RegisterElementController registerElementController;

    public MainControllerTest(){

        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        mainController = new MainController();
        registerElementController = new RegisterElementController();
    }

    @Test(expected = Exception.class)
    public void testIfQRCodeOutOfBoundsGeneratesException() throws Exception{
        String qrCode = "1000";

        registerElementController.associateElementByQrCode(qrCode, context);
    }

    @Test(expected = Exception.class)
    public void testIfInvalidQRCodeGeneratesException() throws Exception{
        String qrCode = "testInvalid";
        registerElementController.associateElementByQrCode(qrCode, context);
    }
}

