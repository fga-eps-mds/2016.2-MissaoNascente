package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import gov.jbb.missaonascente.dao.EnergyRequest;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.dao.SendEnergyRequest;
import gov.jbb.missaonascente.model.Explorer;

public class EnergyController {

    public final int JUST_DECREASE_ENERGY = 0;
    public final int JUST_INCREASE_ENERGY = 1;
    public final int DECREASE_ENERGY_FOR_SHORT_TIME = 2;
    public final int DECREASE_ENERGY = 10;
    public final int INCREMENT_FOR_TIME = 1;   // Each 6 seconds

    private final int MAX_ENERGY = 100;   // Takes 10 minutes to get the max Energy from 0
    private final int MIN_ENERGY = 0;
    private final long MIN_TIME = 120000;     // 2 minutes
    private SharedPreferences preferencesTime;
    private ExplorerDAO explorerDAO;
    private long elapsedElementTime;
    private Explorer explorer = new Explorer();
    private LoginController loginController = new LoginController();

    private final String TAG = "EnergyController";

    public EnergyController(){}

    public  EnergyController(Context context){
        loginController.loadFile(context);

        explorer = loginController.getExplorer();

        explorerDAO = new ExplorerDAO(context);
        explorerDAO.getReadableDatabase();

        Log.d("Initial value in DB: ", String.valueOf(explorerDAO.findEnergy(explorer.getEmail())));

        explorer.setEnergy(explorerDAO.findEnergy(explorer.getEmail()));
        Log.d(TAG,"Value after set energy: " + explorer.getEnergy());
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }

    public long getElapsedElementTime() {
        return elapsedElementTime;
    }

    public void setElapsedElementTime(long elapsedElementTime) {
        this.elapsedElementTime = elapsedElementTime;
    }

    public int getMAX_ENERGY() {
        return MAX_ENERGY;
    }

    public String getRemainingTimeInMinutes(){
        return String.format("%.2f", (MIN_TIME/60000.0f - getElapsedElementTime()/60000.0f));
    }

    public int energyProgress(int energyBarMaxValue){
        return explorer.getEnergy() * energyBarMaxValue / getMAX_ENERGY();
    }

    public void setExplorerEnergyInDataBase(int currentEnergy, int updateEnergy) {
        int actualEnergy;
        actualEnergy = currentEnergy + updateEnergy;

        if(actualEnergy < MIN_ENERGY){
            actualEnergy = MIN_ENERGY;
        } else if(actualEnergy > MAX_ENERGY){
            actualEnergy = MAX_ENERGY;
        }

        explorer.setEnergy(actualEnergy);
        explorerDAO.getWritableDatabase();
        explorerDAO.updateEnergy(explorer);
        Log.d("ENERGY AFTER UPDATE: ", String.valueOf(explorerDAO.findEnergy(explorer.getEmail())));
    }

    public void updateEnergyQuantity(long elapsedEnergyTime){
        if(elapsedEnergyTime < 0){
            elapsedEnergyTime = 600000;
        }

        int elapsedEnergy;

        Log.d("TIME: ", String.valueOf(elapsedEnergyTime));
        elapsedEnergy = (int)(elapsedEnergyTime / 6000); // 6000 - Time to charge one amount of energy
        Log.d("Energy Elapsed: ", String.valueOf(elapsedEnergy));
        if(elapsedEnergy + explorer.getEnergy() >= MAX_ENERGY) {
            explorer.setEnergy(MAX_ENERGY);
            Log.d("In elapsed 100",String.valueOf(getExplorer().getEnergy()));
        }else{
            explorer.setEnergy(elapsedEnergy + explorer.getEnergy());
            Log.d("In elapsed",String.valueOf(getExplorer().getEnergy()));
        }
    }

    public void calculateElapsedEnergyTime(Context context){
        preferencesTime = PreferenceManager.getDefaultSharedPreferences(context);
        long end = System.currentTimeMillis();
        long start = preferencesTime.getLong("energyTime",0);
        if(start > end)
        updateEnergyQuantity(end - start);
        setExplorerEnergyInDataBase(explorer.getEnergy(),0);
        //sendEnergy(context);
    }

    public void addEnergyTimeOnPreferencesTime(){
        SharedPreferences.Editor editor = preferencesTime.edit();
        editor.putLong("energyTime", System.currentTimeMillis());
        editor.apply();
    }

    public void calculateElapsedElementTime(Context context, int elementEnergy){
        if(elementEnergy > 0){
            preferencesTime = PreferenceManager.getDefaultSharedPreferences(context);
            long end = System.currentTimeMillis();
            long start = preferencesTime.getLong("elementTime",0);
            setElapsedElementTime(end - start);
        }
    }

    public void addElementTimeOnPreferencesTime(){
        SharedPreferences.Editor editor = preferencesTime.edit();
        editor.putLong("elementTime", System.currentTimeMillis());
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
            if (getElapsedElementTime() >= MIN_TIME){
                setExplorerEnergyInDataBase(explorer.getEnergy(), elementEnergy);
                addElementTimeOnPreferencesTime();
                return JUST_INCREASE_ENERGY;
            }else{
                setExplorerEnergyInDataBase(explorer.getEnergy(), -(DECREASE_ENERGY));
                return DECREASE_ENERGY_FOR_SHORT_TIME;
            }
        }
    }

    public void checkEnergeticValueElement(int elementEnergy){
        if(elementEnergy > 0){
            setElapsedElementTime(MIN_TIME);
        }
    }
}