package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.RegisterController;
import com.example.jbbmobile.dao.ExplorerDAO;

import org.junit.Before;
import org.junit.Test;


public class RegisterControllerTest {

    private ExplorerDAO explorerDAO;
    private Context context;
    private RegisterController registerController;

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
        registerController = new RegisterController();
    }

    @Test(expected = Exception.class)
    public void testIfHasError() throws Exception {
        registerController.registerError(context);
        registerController.checkIfHasError(context);
    }
}
