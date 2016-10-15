package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.PreferenceController;
import com.example.jbbmobile.controller.RegisterController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;



public class PreferenceControllerTest {
    private PreferenceController controller;
    private Explorer explorer;
    private Context context;
    private ExplorerDAO explorerDAO;
    @Before
    public void setup() throws Exception{
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        controller = new PreferenceController();
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
    }
    @Test
    public void testIfNicknameWasAltered() throws Exception{
        new RegisterController().Register("User", "user@user.com", "000000", "000000", context);
        controller.updateNickname("Resu","user@user.com", context);
        explorer = explorerDAO.findExplorer("user@user.com");
        LoginController loginController = new LoginController();
        loginController.realizeLogin(explorer.getEmail(), explorer.getPassword(), context);
        loginController.loadFile(context);
        assertEquals("Resu", loginController.getExplorer().getNickname());
    }

}
