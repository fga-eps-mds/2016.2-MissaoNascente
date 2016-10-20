package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.RegisterExplorerController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RegisterExplorerControllerTest {

    private ExplorerDAO explorerDAO;
    private Context context;
    private RegisterExplorerController registerExplorerController;

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
        registerExplorerController = new RegisterExplorerController();
    }

    @Test
    public void testIfRegisterWasMade() throws Exception{
        RegisterExplorerController registerExplorerController = new RegisterExplorerController();
        registerExplorerController.Register("Users", "user@user.com", "000000","000000", context);
        while(!registerExplorerController.isAction());
        new ExplorerDAO(context).deleteExplorer(new Explorer("Users", "user@user.com", "000000", "000000"));
        assertEquals(true, registerExplorerController.isResponse());
    }

}
