/*
package com.example.jbbmobile;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.example.jbbmobile.controller.ExplorerController;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.RegisterExplorerController;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.model.Explorer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ExplorerControllerTest {
    private static ExplorerDAO explorerDAO;
    private static Context context;
    private static LoginController loginController;
    private static RegisterExplorerController registerController;
    private static ExplorerController explorerController;
    private static Explorer explorer;
    private static ElementDAO elementDAO;

    @BeforeClass
    public static void setup(){
        context = InstrumentationRegistry.getTargetContext();
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
        loginController = new LoginController();
        registerController = new RegisterExplorerController();
        explorerController = new ExplorerController();
        elementDAO = new ElementDAO(context);
        elementDAO.onUpgrade(elementDAO.getWritableDatabase(), 1,1);
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

        assertEquals(true,explorerController.isResponse());
    }


    @Test
    public void testIfUserElementsWereUpdatedOnOnlineDatabase() throws Exception{

        Element element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "",15.123f,14.123f);
        String date = "24 de outubro de 2016";

        elementDAO.insertElementExplorer(element.getIdElement(), "testUser@user.com",date,"");

        explorerController.insertExplorerElement(context, "testUser@user.com", element.getIdElement(),
                "", date);

        while(!explorerController.isAction());

        assertTrue(explorerController.isResponse());
    }


    @Test
    public void testIfUserElementsWereUpdatedOnLocalDatabase() throws Exception{
        ElementDAO database = new ElementDAO(context);
        database.deleteAllElementsFromElementExplorer(database.getWritableDatabase());
        Element element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "",15.123f,14.123f);
        String date = "24 de outubro de 2016";
        explorerController.updateElementExplorerTable(context, "testUser@user.com");

        while(!explorerController.isAction());

        database.findElementFromRelationTable(element.getIdElement(),"testUser@user.com");
    }

}
*/
