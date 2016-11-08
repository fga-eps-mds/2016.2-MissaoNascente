package com.example.jbbmobile.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppUpdateReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        MainController mainController = new MainController();
        if(mainController.checkIfUserHasInternet(context)) {
            Intent update = new Intent(context, AppUpdateService.class);
            context.startService(update);
        }
    }
}
