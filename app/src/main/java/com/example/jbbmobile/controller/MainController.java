package com.example.jbbmobile.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.jbbmobile.view.ElementScreenActivity;
import com.example.jbbmobile.view.ReadQRCodeScreen;
import com.example.jbbmobile.view.RegisterElementActivity;
import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by igor on 02/10/16.
 */

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

    public Intent checkIfUserHasScannedElement(String code, Context context){
        Intent intent;
        if(!userHasElement(code)) { //checa se não tem elemento
            intent = new Intent(context, RegisterElementActivity.class);
            intent.putExtra("code", code);
        }else{
            intent = new Intent(context, ElementScreenActivity.class);
            intent.putExtra("code", code);
        }

        return intent;
    }

    private boolean userHasElement(String code) {

        return true;
    }
}
