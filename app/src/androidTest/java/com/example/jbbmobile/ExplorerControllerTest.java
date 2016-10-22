package com.example.jbbmobile;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.example.jbbmobile.controller.ExplorerController;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.RegisterExplorerController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class ExplorerControllerTest {
    private ExplorerDAO explorerDAO;
    private Context context;
    private LoginController loginController;
    private RegisterExplorerController registerController;
    private ExplorerController explorerController;
    private Explorer explorer;
    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
        loginController = new LoginController();
        registerController = new RegisterExplorerController();
        explorerController = new ExplorerController();
    }
    @Test
    public void testIfUpdateScoreOnOnlineDatabaseWasMade() throws Exception{
        registerController.register("testUser0", "testUser@user.com", "000000", "000000",
                context);
        while(!registerController.isAction());
        loginController.doLogin("testUser@user.com", "000000", context);
        while(!loginController.isAction());
        loginController.getExplorer().setScore(10);
        explorerController.updateExplorerScore(context,loginController.getExplorer().getScore(),loginController.getExplorer().getEmail());
        //If user don't has internet access the explorerController.osResponse() will be false
        assertEquals(false, explorerController.isResponse());
    }
}