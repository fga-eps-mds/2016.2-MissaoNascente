package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.RegisterExplorerController;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginControllerTest {

    private ExplorerDAO explorerDAO;
    private Context context;
    private LoginController loginController;
    private RegisterExplorerController registerExplorerController;
    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
        loginController = new LoginController();
        registerExplorerController = new RegisterExplorerController();
    }

    @Test
    public void testIfLoginOnOnlineDatabaseWasMade() throws Exception{
        registerExplorerController.register("testUser0", "testUser@user.com", "000000", "000000", context);
        while(!registerExplorerController.isAction());
        loginController.doLogin("testUser@user.com", "000000", context);
        while(!loginController.isAction());
        Thread.sleep(100);
        assertEquals(true, loginController.isResponse());
        new ExplorerDAO(context).deleteExplorer(new Explorer("testUser0", "testUser@user.com", "000000", "000000"));
    }
}
