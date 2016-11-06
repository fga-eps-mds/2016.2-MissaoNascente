package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.AlternativeController;
import com.example.jbbmobile.dao.AlternativeDAO;
import com.example.jbbmobile.model.Alternative;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlternativeControllerTest {
    private Context context;
    private AlternativeController controller;
    private AlternativeDAO alternativeDAO;

    @Before
    public void setup() throws Exception{
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        controller = new AlternativeController();
        alternativeDAO = new AlternativeDAO(context);
        alternativeDAO.onUpgrade(alternativeDAO.getReadableDatabase(),2,2);
    }

    @Test
    public void testIfQuestionsWereDownloaded() throws Exception{
        controller.downloadAllAlternatives(context);
        while(!controller.isAction());
        Alternative alternative = alternativeDAO.findAlternative(1);
        assertEquals(1,alternative.getIdAlternative());
    }
}