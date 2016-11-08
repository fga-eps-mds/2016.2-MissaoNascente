package com.example.jbbmobile;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.AppUpdateReceiver;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppUpdateReceiverTest {
    private Context context;
    private ElementDAO elementDAO;
    private final int ELEMENT_ID = 1;
    @Before
    public void setup(){
        context = InstrumentationRegistry.getTargetContext();
        elementDAO = new ElementDAO(context);
        elementDAO.onUpgrade(elementDAO.getWritableDatabase(), 1,1);
    }

    @Test
    public void testIfUpdateInBackgroundIsSuccessful() throws Exception{
        Intent update = new Intent(context, AppUpdateReceiver.class);
        context.sendBroadcast(update);
        Thread.sleep(3000);
        Element element = elementDAO.findElementFromElementTable(ELEMENT_ID);
        assertEquals(ELEMENT_ID, element.getIdElement());
    }

}
