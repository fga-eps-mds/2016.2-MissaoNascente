package gov.jbb.missaonascente;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.controller.EnergyController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnergyControllerTest {
    private final String USER_EMAIL = "user@user.com";
    private final String USER_PASSWORD = "000000";
    private final String USER_NICKNAME = "User";
    private Context context;
    private EnergyController energyController;
    private LoginController loginController;
    private ExplorerDAO explorerDAO;

    @Before
    public void setup() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getWritableDatabase(), 1,1);
        Explorer explorer = new Explorer(USER_NICKNAME, USER_EMAIL, USER_PASSWORD, USER_PASSWORD);
        explorerDAO.insertExplorer(explorer);
        loginController = new LoginController();
        loginController.realizeLogin(USER_EMAIL, USER_PASSWORD, context);
        energyController = new EnergyController(context);
    }

    @Test
    public void testIfEnergyIncrementationWasSetInDatabase() throws Exception{
        Explorer explorer = energyController.getExplorer();

        ExplorerDAO explorerDAO = new ExplorerDAO(context);
        int energy = explorerDAO.findEnergy(explorer.getEmail());

        energyController.setExplorerEnergyInDataBase(energy,1);

        assertEquals(energy, explorerDAO.findEnergy(explorer.getEmail()));
    }

    @Test
    public void testIfEnergyProgressIsCorrect() throws Exception{
        Explorer explorer = energyController.getExplorer();
        explorer.setEnergy(50);

        int progress = energyController.energyProgress(50);

        assertEquals(progress, 25);
    }

    @Test
    public void textIfElapsedTimePlusEnergyPassTheMax() throws Exception{
        Explorer explorer = energyController.getExplorer();
        explorer.setEnergy(90);

        energyController.updateEnergyQuantity(60000);

        assertEquals(explorer.getEnergy(), 100);
    }

    @Test
    public void textIfElapsedTimePlusEnergyNoPassTheMax() throws Exception{
        Explorer explorer = energyController.getExplorer();
        explorer.setEnergy(50);

        energyController.updateEnergyQuantity(6000);

        assertEquals(explorer.getEnergy(), 51);
    }

    @After
    public void deleteExplrorer() throws Exception{
        new ExplorerDAO(context).deleteExplorer(new Explorer(USER_NICKNAME, USER_EMAIL, USER_PASSWORD, USER_PASSWORD));
    }

}
