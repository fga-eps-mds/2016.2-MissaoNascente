package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.jbbmobile.dao.EnergyRequest;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.dao.SendEnergyRequest;
import com.example.jbbmobile.model.Explorer;

public class EnergyController {

    public final int JUST_DECREASE_ENERGY = 0;
    public final int JUST_INCREASE_ENERGY = 1;
    public final int DECREASE_ENERGY_FOR_SHORT_TIME = 2;
    public final int DECREASE_ENERGY = 10;
    public final int INCREMENT_FOR_TIME = 1;

    private final int MAX_ENERGY = 100;
    private final int MIN_ENERGY = 0;
    private final int MIN_TIME = 30000;
    private SharedPreferences preferencesEnergyTime;
    private SharedPreferences preferencesElementTime;
    private ExplorerDAO explorerDAO;
    private Explorer explorer = new Explorer();
    private LoginController loginController = new LoginController();

    private final String TAG = "EnergyController";

    public EnergyController(){}

    public  EnergyController(Context context){
        loginController.loadFile(context);

        explorer = loginController.getExplorer();

        explorerDAO = new ExplorerDAO(context);
        explorerDAO.getReadableDatabase();

        explorer.setEnergy(explorerDAO.findEnergy(explorer.getEmail()));
        Log.d(TAG,"Initial value in DataBase " + Integer.toString(explorer.getEnergy()));
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }

    public int getMAX_ENERGY() {
        return MAX_ENERGY;
    }

    public int energyProgress(int energyBarMaxValue){
        return explorer.getEnergy() * energyBarMaxValue / getMAX_ENERGY();
    }

    public void setExplorerEnergyInDataBase(int currentEnergy, int updateEnergy) {
        int actualEnergy;
        actualEnergy = currentEnergy + updateEnergy;

        if(actualEnergy< MIN_ENERGY){
            actualEnergy = MIN_ENERGY;
        } else if(actualEnergy> MAX_ENERGY){
            actualEnergy = MAX_ENERGY;
        }

        explorer.setEnergy(actualEnergy);
        explorerDAO.getWritableDatabase();
        explorerDAO.updateEnergy(explorer);
    }

    public void updateEnergyQuantity(long elapsedEnergyTime){
        int elapsedEnergy;

        Log.d("TIME", String.valueOf(elapsedEnergyTime));
        elapsedEnergy = (int)(elapsedEnergyTime / 5000); // 5000 - Time to charge one amount of energy
        if(elapsedEnergy + explorer.getEnergy() >= MAX_ENERGY) {
            explorer.setEnergy(MAX_ENERGY);
            Log.d("In elapsed 100","Energy: " + String.valueOf(getExplorer().getEnergy()));
        }else{
            explorer.setEnergy(elapsedEnergy + explorer.getEnergy());
            Log.d("In elapsed","Energy: " + String.valueOf(getExplorer().getEnergy()));
        }
    }

    public void calculateElapsedEnergyTime(Context context){
        preferencesEnergyTime = PreferenceManager.getDefaultSharedPreferences(context);
        long end = System.currentTimeMillis();
        long start = preferencesEnergyTime.getLong("time",0);

        updateEnergyQuantity(end - start);
        setExplorerEnergyInDataBase(explorer.getEnergy(),0);
        sendEnergy(context);
    }

    public void addTimeOnPreferencesEnergyTime(){
        SharedPreferences.Editor editor = preferencesEnergyTime.edit();
        editor.putLong("time", System.currentTimeMillis());
        editor.apply();
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

    public int checkElementsEnergyType (int elementEnergy){
        if(elementEnergy <= 0){
            setExplorerEnergyInDataBase(explorer.getEnergy(), elementEnergy);
            return JUST_DECREASE_ENERGY;
        } else {
            if (30000 >= MIN_TIME){
                setExplorerEnergyInDataBase(explorer.getEnergy(), elementEnergy);
                return JUST_INCREASE_ENERGY;
            }else{
                setExplorerEnergyInDataBase(explorer.getEnergy(), -(DECREASE_ENERGY));
                return DECREASE_ENERGY_FOR_SHORT_TIME;
            }
        }
    }
}