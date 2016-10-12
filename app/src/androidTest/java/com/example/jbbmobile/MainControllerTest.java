package com.example.jbbmobile;

import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.view.MainScreenActivity;
import com.example.jbbmobile.view.PreferenceScreenActivity;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MainControllerTest {
    @Rule
    public final ActivityTestRule<MainScreenActivity> mainScreen;

    private Context context;
    private MainController mainController;
    private ElementsController elementsController;

    public MainControllerTest(){
        mainScreen = new ActivityTestRule<>(MainScreenActivity.class);
        mainScreen.launchActivity(new Intent());
        context = mainScreen.getActivity();
        mainController = new MainController();
        elementsController = new ElementsController();
    }

    @Test
    public void testIfValidQRCodeReturnsID() throws Exception{
        String qrCode = "1";
        Element element = elementsController.associateElementbyQrCode(qrCode, context);
        int idElement = element.getIdElement();
        assertEquals(idElement, 1);
    }

    @Test(expected = Exception.class)
    public void testIfQRCodeOutOfBoundsGeneratesException() throws Exception{
        String qrCode = "1000";
        Element element = elementsController.associateElementbyQrCode(qrCode, context);
    }

    @Test(expected = Exception.class)
    public void testIfInvalidQRCodeGeneratesException() throws Exception{
        String qrCode = "testInvalid";
        Element element = elementsController.associateElementbyQrCode(qrCode, context);
    }
}

