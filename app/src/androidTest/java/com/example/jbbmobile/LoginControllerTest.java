package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

import org.junit.Before;
import org.junit.Test;


public class LoginControllerTest {

    private ExplorerDAO explorerDAO;
    private Context context;
    private LoginController loginController;

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
        loginController = new LoginController();
    }

    @Test
    public void testIfLoginWasMade() throws Exception {
        Explorer explorer = new Explorer("User", "user@user.com", "123456","123456");
        explorerDAO.insertExplorer(explorer);
        loginController.doLogin("user@user.com", "123456", context);
        loginController.loadFile(context);
        explorerDAO.deleteExplorer(explorer);
    }

    @Test(expected = Exception.class)
    public void testIfLoginWasNotMade() throws Exception {
        loginController.doLogin("user@user.com", "123456", context);
        loginController.loadFile(context);
    }
}
