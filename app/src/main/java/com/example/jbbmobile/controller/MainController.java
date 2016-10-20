package com.example.jbbmobile.controller;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.jbbmobile.view.ReadQRCodeScreen;
import com.google.zxing.integration.android.IntentIntegrator;

public class MainController {
    private String code;

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

}
