package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.controller.RegisterExplorerController;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;

import org.junit.After;
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
        new ExplorerDAO(context).deleteExplorer(new Explorer("OtherUsers", "user2@user.com.br", "000000", "000000"));
        RegisterExplorerController registerExplorerController = new RegisterExplorerController();
        registerExplorerController.register("OtherUsers", "user2@user.com.br", "000000","000000", context);
        while(!registerExplorerController.isAction());
        assertEquals(true, registerExplorerController.isResponse());
        new ExplorerDAO(context).deleteExplorer(new Explorer("OtherUsers", "user2@user.com.br", "000000", "000000"));
    }

    @After
    public void tearDown(){
        LoginController loginController = new LoginController();
        loginController.deleteFile(context);
    }

}
