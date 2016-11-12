package gov.jbb.missaonascente;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.controller.RegisterExplorerController;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;

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
        registerExplorerController.register("Users", "user@user.com.br", "000000","000000", context);
        while(!registerExplorerController.isAction());
        Thread.sleep(200);
        assertEquals(true, registerExplorerController.isResponse());
        new ExplorerDAO(context).deleteExplorer(new Explorer("Users", "user@user.com.br", "000000", "000000"));
    }

}
