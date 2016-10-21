package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

public class EnergyController {

    private int maxEnergy = 100;
    private SharedPreferences preferences;
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
        explorer.setEnergy(currentEnergy + 1);

        explorerDAO.getWritableDatabase();
        explorerDAO.updateEnergy(explorer);
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int energyProgress(int energyBarMaxValue){
        return explorer.getEnergy() * energyBarMaxValue / getMaxEnergy();
    }

    private void updateEnergyQuantity(long elapsedTime){
        int elapsedTimeEnergy;

        Log.d("TIME", String.valueOf(elapsedTime));
        elapsedTimeEnergy = (int)(elapsedTime / 5000); // 5000 - Time to charge one amount of energy
        if(elapsedTimeEnergy + explorer.getEnergy() > maxEnergy)
            explorer.setEnergy(maxEnergy-1);
        else
            explorer.setEnergy(elapsedTimeEnergy + explorer.getEnergy());
    }

    public void calculateElapsedTime(Context context){
        long end = System.currentTimeMillis();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        long start = preferences.getLong("time",0);

        updateEnergyQuantity(end - start);
    }

    public void addTimeOnPreferences(){
        SharedPreferences.Editor ed = preferences.edit();
        ed.putLong("time", System.currentTimeMillis());
        ed.commit();
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }
}
