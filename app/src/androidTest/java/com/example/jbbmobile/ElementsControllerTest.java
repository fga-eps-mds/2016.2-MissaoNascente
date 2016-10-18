package com.example.jbbmobile;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.ElementsController;
import org.junit.Test;


public class ElementsControllerTest {
    private Context context;
    private ElementsController elementsController;

    public ElementsControllerTest(){
        this.context = InstrumentationRegistry.getTargetContext();
        elementsController = new ElementsController();
    }

    @Test (expected = SQLException.class)
    public void testIfFindElementByIDGenerateException () throws  Exception{
        String email = "test@test.com";
        int idElement = 1;
        elementsController.findElementByID(idElement,email,context);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIfAssociateElementByQrCodeGenerateIllegalArgumentException() throws Exception{
        String qrCode = "8";
        elementsController.associateElementByQrCode(qrCode,context);
    }

}

















