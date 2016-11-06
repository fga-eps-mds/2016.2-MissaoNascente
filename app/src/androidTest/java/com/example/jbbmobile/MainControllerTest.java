package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import com.example.jbbmobile.controller.LoginController;

import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.controller.RegisterElementController;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.view.StartScreenActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class MainControllerTest {
    private Context context;
    private MainController mainController;
    private ElementDAO elementDAO;

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        mainController = new MainController();
        elementDAO = new ElementDAO(context);
        elementDAO.onUpgrade(elementDAO.getWritableDatabase(), 10, 10);
    }

    @Test
    public void checkIfDatabaseVersionIsDifferent() throws InterruptedException {
        Thread.sleep(3000);
        elementDAO.onUpgrade(elementDAO.getWritableDatabase(), 10, 10);
        mainController.checkIfUpdateIsNeeded(context);
        elementDAO.checkVersion();
        while(!mainController.isAction());
        elementDAO.updateVersion(-1f);
        assertTrue(mainController.isResponse());
    }
}

