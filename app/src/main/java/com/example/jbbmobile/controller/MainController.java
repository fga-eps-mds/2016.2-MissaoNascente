package com.example.jbbmobile.controller;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.dao.UpdateScoreRequest;
import com.example.jbbmobile.view.ReadQRCodeScreen;
import com.google.zxing.integration.android.IntentIntegrator;

public class MainController {
    private String code;
    private ExplorerDAO explorerDAO;
    private boolean action = false;
    private boolean response;

    public MainController(){
    }

    public MainController(Activity activity){
        IntentIntegrator Integrator = new IntentIntegrator(activity);
        Integrator.setOrientationLocked(true);
        Integrator.setCaptureActivity(ReadQRCodeScreen.class);
        Integrator.initiateScan();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean checkIfUserHasInternet(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public ExplorerDAO getDao() {
        return explorerDAO;
    }

    public void setDao(ExplorerDAO explorerDAO) {
        this.explorerDAO = explorerDAO;
    }

    public boolean updateExplorerScore( final Context preferenceContext,int score,String email) {
        try{
            Log.i("****Explorer: ",""+score);
            UpdateScoreRequest updateScoreRequest = new UpdateScoreRequest(score,email);
            updateScoreRequest.request(preferenceContext, new UpdateScoreRequest.Callback() {
                @Override
                public void callbackResponse(boolean response) {
                    setResponse(response);
                    setAction(true);
                    if(!response){

                    }
                }
            });
        }catch(SQLiteConstraintException exception){
            throw exception;
        }

        return true;
    }
}
