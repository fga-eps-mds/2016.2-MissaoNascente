package com.example.jbbmobile;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.model.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementsControllerTest {
    private Context context;
    private ElementsController elementsController;

    public ElementsControllerTest(){
        this.context = InstrumentationRegistry.getTargetContext();
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
        elementsController.findElementByQrCode(qrCode, context);
    }

    @Test(expected = Exception.class)
    public void testIfQRCodeOutOfBoundGeneratesException () throws Exception {
        int qrCode = 1000;
        elementsController.findElementByQrCode(qrCode, context);
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

















