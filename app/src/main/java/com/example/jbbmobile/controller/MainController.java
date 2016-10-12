package com.example.jbbmobile.controller;

import android.app.Activity;
import android.content.Context;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.view.ReadQRCodeScreen;
import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by igor on 02/10/16.
 */

public class MainController {
    public String code;

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




    private boolean userHasElement(int idElement) {

        return true;
    }
}
