package gov.jbb.missaonascente;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import gov.jbb.missaonascente.controller.EnergyController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.min;
import static org.junit.Assert.*;

public class EnergyControllerTest {
    private final String USER_EMAIL = "user@user.com";
    private final String USER_PASSWORD = "000000";
    private final String USER_NICKNAME = "User";
    private final long MIN_TIME = 120000;
    private final int MIN_ENERGY = 0;
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

        LoginController loginController = new LoginController();
        loginController.loadFile(context);
        Explorer explorer = loginController.getExplorer();

        Log.d("Eita", explorer.toString());

        ExplorerDAO explorerDAO = new ExplorerDAO(context);
        int energy = explorerDAO.findEnergy(explorer.getEmail());

        energyController.setExplorerEnergyInDataBase(energy, 1);

        energy = min(energy + 1, 100);
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
    public void testIfElapsedTimePlusEnergyPassTheMax() throws Exception{
        Explorer explorer = energyController.getExplorer();
        explorer.setEnergy(90);

        energyController.updateEnergyQuantity(60000);

        assertEquals(explorer.getEnergy(), 100);
    }

    @Test
    public void testIfElapsedTimePlusEnergyNoPassTheMax() throws Exception{
        Explorer explorer = energyController.getExplorer();
        explorer.setEnergy(50);

        energyController.updateEnergyQuantity(6000);

        assertEquals(explorer.getEnergy(), 51);
    }

    @Test
    public void testIfEnergySettedInDatabaseIsNotLowerThanTheMin () throws Exception {
        int currentEnergy = 100;
        int updateEnergy = -150;
        energyController.setExplorerEnergyInDataBase(currentEnergy, -updateEnergy);

        Explorer explorer = energyController.getExplorer();
        int actualEnergy = explorerDAO.findEnergy(explorer.getEmail());

        assert actualEnergy == MIN_ENERGY;
    }

    @Test
    public void testIfTheRemainingTimeInMinutesAreInTheCorrectFormat () throws Exception {
        long elapsedTime = energyController.getElapsedElementTime();
        float remainingTime = (MIN_TIME/60000.0f - elapsedTime/60000.0f);

        assertEquals(energyController.getRemainingTimeInMinutes(), String.format("%.2f", remainingTime));
    }

    @After
    public void deleteExplorer() throws Exception{
        new ExplorerDAO(context).deleteExplorer(new Explorer(USER_NICKNAME, USER_EMAIL, USER_PASSWORD, USER_PASSWORD));
    }

}
