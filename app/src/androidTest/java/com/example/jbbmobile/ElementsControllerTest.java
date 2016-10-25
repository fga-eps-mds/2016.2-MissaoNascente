package com.example.jbbmobile;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElementsControllerTest {
    private Context context;
    private ElementsController elementsController;
    private ElementDAO elementDAO;
    private final int idElement = 1;

    public void ElementsControllerTest(){
        this.context = InstrumentationRegistry.getTargetContext();
        elementsController = new ElementsController();
        elementDAO = new ElementDAO(context);
    }

    @Test
    public void testFindElementByID() throws  Exception{
        String email = "test@test.com";
        int idElement = 1;
        Element element;
        element = elementsController.findElementByID(idElement,email,context);
        assertEquals(element.getIdElement(),idElement);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIfAssociateElementByQrCodeGenerateIllegalArgumentException() throws Exception{
        String qrCode = "8";
        elementsController.associateElementByQrCode(qrCode,context);
    }

    @Test
    public void testIfElementsWereDownloadedFromOnlineDatabase() throws Exception{
        elementDAO.onUpgrade(elementDAO.getWritableDatabase(), 1, 1);
        elementsController.downloadElementsFromDatabase(context);
        while(!elementsController.isAction());
        elementDAO.findElementFromElementTable(idElement);
    }
}

















