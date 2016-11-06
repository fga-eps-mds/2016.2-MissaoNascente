package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.RegisterExplorerController;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.model.Explorer;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElementsControllerTest {
    private final String USER_EMAIL = "user@user.com";
    private final String USER_PASSWORD = "000000";
    private final String USER_NICKNAME = "User";
    private LoginController loginController;
    private Context context;
    private ElementsController elementsController;
    private ElementDAO elementDAO;
    private ExplorerDAO explorerDAO;
    private final int idElement = 1;


    @Before
    public void setUp(){
        this.context = InstrumentationRegistry.getTargetContext();
        elementsController = new ElementsController();
        elementDAO = new ElementDAO(context);
        explorerDAO = new ExplorerDAO(context);
        loginController = new LoginController();
        explorerDAO.onUpgrade(explorerDAO.getWritableDatabase(), 1,1);
        elementDAO.onUpgrade(elementDAO.getWritableDatabase(), 1,1);
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
        explorerDAO.onUpgrade(explorerDAO.getWritableDatabase(), 1,1);
        RegisterExplorerController registerExplorerController = new RegisterExplorerController();
        registerExplorerController.register(USER_NICKNAME, USER_EMAIL, USER_PASSWORD, USER_PASSWORD, context);
        while(!registerExplorerController.isAction());
        loginController.realizeLogin(USER_EMAIL, USER_PASSWORD, context);
        String qrCode = "8";
        elementsController.associateElementByQrCode(qrCode,context);
        explorerDAO.deleteExplorer(new Explorer(USER_NICKNAME, USER_EMAIL, USER_PASSWORD, USER_PASSWORD));
    }

    @Test
    public void testIfElementsWereDownloadedFromOnlineDatabase() throws Exception{
        elementsController.downloadElementsFromDatabase(context);
        while(!elementsController.isAction());

        assertNotNull(elementDAO.findElementFromElementTable(idElement));
    }
}

















