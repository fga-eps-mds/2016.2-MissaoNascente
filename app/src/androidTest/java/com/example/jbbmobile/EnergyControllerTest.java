/*
package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.controller.EnergyController;
import com.example.jbbmobile.controller.RegisterExplorerController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;
import com.example.jbbmobile.view.MainScreenActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnergyControllerTest {
    private Context context;
    private EnergyController energyController;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        energyController = new EnergyController(context);
    }

    @Test
    public void testIfEnergyIncrementationWasSetInDatabase() throws Exception{
        Explorer explorer = energyController.getExplorer();

        ExplorerDAO explorerDAO = new ExplorerDAO(context);
        int energy = explorerDAO.findEnergy(explorer.getEmail());

        energyController.setExplorerEnergyInDataBase(energy,1);

        assertEquals(energy+1, explorerDAO.findEnergy(explorer.getEmail()));
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

        energyController.updateEnergyQuantity(50000);

        assertEquals(explorer.getEnergy(), 100);
    }

    @Test
    public void textIfElapsedTimePlusEnergyNoPassTheMax() throws Exception{
        Explorer explorer = energyController.getExplorer();
        explorer.setEnergy(50);

        energyController.updateEnergyQuantity(5000);

        assertEquals(explorer.getEnergy(), 51);
    }

}
*/
