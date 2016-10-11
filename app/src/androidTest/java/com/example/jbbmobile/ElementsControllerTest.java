package com.example.jbbmobile;

import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.view.ElementScreenActivity;
import com.example.jbbmobile.view.MainScreenActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by hugo on 10/10/16.
 */

public class ElementsControllerTest {
    @Rule
    public final ActivityTestRule<ElementScreenActivity> elementScreen;

    private Context context;
    private ElementsController elementsController;

    public ElementsControllerTest(){
        elementScreen = new ActivityTestRule<>(ElementScreenActivity.class);
        elementScreen.launchActivity(new Intent());
        context = elementScreen.getActivity();
        elementsController = new ElementsController();
    }

    @Test
    public void testIfValidQRCodeGeneratesValidElement () throws Exception {
        int qrCode = 2;
        Element element = elementsController.findElementByQrCode(qrCode, context);
        assertEquals(element.getIdElement(), 2);
    }

    @Test(expected = Exception.class)
    public void testIfNegativeQRCodeGeneratesException () throws Exception {
        int qrCode = -3;
        Element element = elementsController.findElementByQrCode(qrCode, context);
    }

    @Test(expected = Exception.class)
    public void testIfQRCodeOutOfBoundGeneratesException () throws Exception {
        int qrCode = 1000;
        Element element = elementsController.findElementByQrCode(qrCode, context);
    }

}

















