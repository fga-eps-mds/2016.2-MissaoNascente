package com.example.jbbmobile.controller;

import android.content.Context;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

public class EnergyController {

    public static final int MAX_ENERGY = 10000;

    private ExplorerDAO explorerDAO;
    private int currentEnergy;
    private Explorer currentExplorer;

    public  EnergyController(Context context){
        LoginController loginController = new LoginController();
        loginController.loadFile(context);
        currentExplorer = loginController.getExplorer();

        explorerDAO = new ExplorerDAO(context);
        explorerDAO.getReadableDatabase();
        currentEnergy = explorerDAO.findEnergy(currentExplorer.getEmail());
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;

        explorerDAO.getWritableDatabase();
        explorerDAO.updateEnergy(currentExplorer);
    }

    public final int energyProgress(int energyBarMaxValue){
        return currentEnergy * energyBarMaxValue / MAX_ENERGY;
    }

}
