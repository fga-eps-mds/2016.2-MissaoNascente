package com.example.jbbmobile.controller;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.widget.Toast;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.VersionRequest;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.view.ReadQRCodeScreen;
import com.google.zxing.integration.android.IntentIntegrator;

import java.lang.reflect.Field;

public class MainController {
    private String code;
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

    public void forceImageIcons(PopupMenu popupMenu){
        Object menuHelper;
        Class[] argTypes;

        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            popupMenu.show();
        }

    }

    public boolean checkIfUserHasInternet(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void checkIfUpdateIsNeeded(final Context context){
        VersionRequest versionRequest = new VersionRequest();

        versionRequest.request(context, new VersionRequest.Callback() {
            @Override
            public void callbackResponse(double response) {
                ElementDAO database = new ElementDAO(context);
                if(database.checkVersion() == response){
                    setResponse(false);
                    Toast.makeText(context, "Não precisa atualizar", Toast.LENGTH_SHORT).show();
                }else{
                    setResponse(true);
                    ElementsController controller = new ElementsController();
                    controller.downloadElementsFromDatabase(context);
                    new ElementDAO(context).updateVersion((float) response);
                }
                setAction(true);
            }
        });
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
}
