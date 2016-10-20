package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

public class EnergyController {

    private int maxEnergy = 100;
    private ExplorerDAO explorerDAO;
    private Explorer explorer = new Explorer();
    private LoginController loginController = new LoginController();

    private final String TAG = "EnergyController";

    public  EnergyController(Context context){
        loginController.loadFile(context);

        explorer = loginController.getExplorer();

        explorerDAO = new ExplorerDAO(context);
        explorerDAO.getReadableDatabase();

        explorer.setEnergy(explorerDAO.findEnergy(explorer.getEmail()));
        Log.d(TAG,"Initial value in DataBase" + Integer.toString(explorer.getEnergy()));
    }

    public void setExplorerEnergyInDataBase(int currentEnergy) {
        explorer.setEnergy(currentEnergy);

        explorerDAO.getWritableDatabase();
        explorerDAO.updateEnergy(explorer);
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public final int energyProgress(int energyBarMaxValue){
        return explorer.getEnergy() * energyBarMaxValue / getMaxEnergy();
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }
}
