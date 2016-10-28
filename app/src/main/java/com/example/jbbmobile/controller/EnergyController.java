package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.jbbmobile.dao.EnergyRequest;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.dao.RegisterRequest;
import com.example.jbbmobile.dao.SendEnergyRequest;
import com.example.jbbmobile.model.Explorer;

public class EnergyController {

    private final int maxEnergy = 100;
    private final int minEnergy = 0;
    private SharedPreferences preferences;
    private ExplorerDAO explorerDAO;
    private Explorer explorer = new Explorer();
    private LoginController loginController = new LoginController();

    private final String TAG = "EnergyController";

    public EnergyController(){
    }

    public  EnergyController(Context context){
        loginController.loadFile(context);

        explorer = loginController.getExplorer();

        explorerDAO = new ExplorerDAO(context);
        explorerDAO.getReadableDatabase();

        explorer.setEnergy(explorerDAO.findEnergy(explorer.getEmail()));
        Log.d(TAG,"Initial value in DataBase " + Integer.toString(explorer.getEnergy()));
    }

    public void setExplorerEnergyInDataBase(int currentEnergy, int updateEnergy) {
        int actualEnergy;
        actualEnergy = currentEnergy + updateEnergy;
        if(actualEnergy<minEnergy){
            actualEnergy = minEnergy;
        }
        explorer.setEnergy(actualEnergy);
        explorerDAO.getWritableDatabase();
        explorerDAO.updateEnergy(explorer);
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int energyProgress(int energyBarMaxValue){
        return explorer.getEnergy() * energyBarMaxValue / getMaxEnergy();
    }

    public void updateEnergyQuantity(long elapsedTime){
        int elapsedTimeEnergy;

        Log.d("TIME", String.valueOf(elapsedTime));
        elapsedTimeEnergy = (int)(elapsedTime / 5000); // 5000 - Time to charge one amount of energy
        if(elapsedTimeEnergy + explorer.getEnergy() >= maxEnergy) {
            explorer.setEnergy(maxEnergy);
            Log.d("In elapsed 100","Energy: " + String.valueOf(getExplorer().getEnergy()));
        }else{
            explorer.setEnergy(elapsedTimeEnergy + explorer.getEnergy());
            Log.d("In elapsed","Energy: " + String.valueOf(getExplorer().getEnergy()));
        }
    }

    public void calculateElapsedTime(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        long end = System.currentTimeMillis();
        long start = preferences.getLong("time",0);

        updateEnergyQuantity(end - start);
        setExplorerEnergyInDataBase(explorer.getEnergy(),0);
        sendEnergy(context);
    }

    public void addTimeOnPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("time", System.currentTimeMillis());
        editor.apply();
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }

    public void synchronizeEnergy (Context context){
        final EnergyRequest energyRequest = new EnergyRequest(explorer.getEmail());

        energyRequest.request(context, new EnergyRequest.Callback() {
            @Override
            public void callbackResponse(boolean success) {
                if(success)
                    explorer.setEnergy(energyRequest.getExplorerEnergy());
                Log.d("REMOTE_ENERGY", "Get Energy: " + energyRequest.getExplorerEnergy());
            }
        });
    }

    public void sendEnergy (Context context){
        SendEnergyRequest sendEnergyRequest = new SendEnergyRequest(explorer.getEmail(), explorer.getEnergy());
        Log.d("REMOTE_ENERGY", "Energy send: " + explorer.getEnergy());

        sendEnergyRequest.request(context, new SendEnergyRequest.Callback() {
            @Override
            public void callbackResponse(boolean success) {
                if(success)
                    Log.d("REMOTE_ENERGY", "Successful update!");
            }
        });
    }
}