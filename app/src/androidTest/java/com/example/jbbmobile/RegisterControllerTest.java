package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.RegisterController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;


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

    @Test
    public void testIfRegisterWasMade() throws Exception{
        RegisterController registerController = new RegisterController();
        registerController.Register("Users", "user@user.com", "000000","000000", context);
        while(!registerController.isAction());
        new ExplorerDAO(context).deleteExplorer(new Explorer("Users", "user@user.com", "000000", "000000"));
        assertEquals(true, registerController.isResponse());
    }

}
