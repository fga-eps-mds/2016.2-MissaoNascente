package gov.jbb.missaonascente;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.controller.ElementsController;
import gov.jbb.missaonascente.controller.ExplorerController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.RegisterExplorerController;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Element;
import gov.jbb.missaonascente.model.Explorer;
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
    private static ElementDAO elementDAO;
    private static ElementsController elementsController;
    private final String EMAIL = "test@test.com";

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
        elementsController = new ElementsController();
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
        new ExplorerDAO(context).deleteExplorer(new Explorer("testUser0", "testUser@user.com", "000000", "000000"));
        assertEquals(true,explorerController.isResponse());
    }

    @Test
    public void testIfUserElementsWereUpdatedOnOnlineDatabase() throws Exception{

        Element element = new Element(1, 1, 100, "ponto_2", "Pau-Santo", 1, "",15.123f,14.123f,10, 1,"Mensagem");
        String date = "24 de outubro de 2016";

        elementDAO.insertElementExplorer(element.getIdElement(), "testUser@user.com",date,"");

        explorerController.insertExplorerElement(context, "testUser@user.com", element.getIdElement(),
                "", date);

        while(!explorerController.isAction());

        assertTrue(explorerController.isResponse());
    }

    @Test
    public void testIfUserElementsWereSentToOnlineDatabase() throws InterruptedException {
        Element element = new Element(1, 1,100, "ponto_2", "Pau-Santo", 1, "", 15.123f, 14.123f,10, 1,"Mensagem");
        String date = "24 de outubro de 2016";
        elementDAO.insertElementExplorer(element.getIdElement(), EMAIL, date,
                element.getDefaultImage());
        explorerController.sendElementsExplorerTable(context, EMAIL);
        Thread.sleep(3000);
        elementDAO.onUpgrade(elementDAO.getWritableDatabase(), 1,1);
        elementDAO.insertElement(element);
        explorerController.updateElementExplorerTable(context, EMAIL);
        Thread.sleep(3000);
        element = elementDAO.findElementFromRelationTable(element.getIdElement(), EMAIL);
        assertEquals(1, element.getIdElement());
    }

    @Test
    public void testIfUserElementsWereUpdatedOnLocalDatabase() throws Exception {
        elementDAO.deleteAllElementsFromElementExplorer(elementDAO.getWritableDatabase());
        Element element = new Element(9, 9, 100, "ponto_2", "Pau-Santo", 1, "", 15.123f, 14.123f,10, 1,"Mensagem");
        String date = "24 de outubro de 2016";
        elementDAO.insertElement(element);
        explorerController.insertExplorerElement(context, "testUser@user.com", element.getIdElement(), "", date);
        while (!explorerController.isAction()) ;
        explorerController.updateElementExplorerTable(context, "testUser@user.com");
        while (!explorerController.isAction()) ;
        elementDAO.findElementFromRelationTable(9, "testUser@user.com");
    }


}