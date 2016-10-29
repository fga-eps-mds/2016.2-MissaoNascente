package com.example.jbbmobile.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jbbmobile.controller.NotificationController;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationController notificationController = new NotificationController(context);
        notificationController.notificationByPeriod();
    }


}
