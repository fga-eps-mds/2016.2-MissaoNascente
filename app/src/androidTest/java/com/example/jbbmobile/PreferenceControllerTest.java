package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.PreferenceController;
import com.example.jbbmobile.controller.RegisterExplorerController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;



public class PreferenceControllerTest {
    private PreferenceController controller;
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
        RegisterExplorerController registerExplorerController = new RegisterExplorerController();
        registerExplorerController.register("testUser4", "testUser4@user.com", "000000", "000000", context);
        while(!registerExplorerController.isAction());
        controller.updateNickname("Resu","testUser4@user.com", context);
        while(!controller.isAction());
        new ExplorerDAO(context).deleteExplorer(new Explorer("testUser4", "testUser4@user.com", "000000", "000000"));
        assertEquals(true, controller.isResponse());
    }

}
