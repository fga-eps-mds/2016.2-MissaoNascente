package com.example.jbbmobile.controller;

import android.app.Activity;

import com.example.jbbmobile.view.ReadQRCodeScreen;
import com.google.zxing.integration.android.IntentIntegrator;

public class MainController {
    public String code;

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
}
