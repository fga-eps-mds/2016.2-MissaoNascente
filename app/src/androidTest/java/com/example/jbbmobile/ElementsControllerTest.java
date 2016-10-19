package com.example.jbbmobile;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.model.Element;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;


public class ElementsControllerTest {
    private Context context;
    private ElementsController elementsController;

    public ElementsControllerTest(){
        this.context = InstrumentationRegistry.getTargetContext();
        elementsController = new ElementsController();
    }

    @Test
    public void testIfFindElementByIDGenerateException () throws  Exception{
        String email = "test@test.com";
        int idElement = 1;
        Element element;
        element = elementsController.findElementByID(idElement,email,context);
        assertNotEquals(element.getIdElement(),idElement);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIfAssociateElementByQrCodeGenerateIllegalArgumentException() throws Exception{
        String qrCode = "8";
        elementsController.associateElementByQrCode(qrCode,context);
    }

}

















