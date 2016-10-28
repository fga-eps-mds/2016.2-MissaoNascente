package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;

import com.example.jbbmobile.dao.NotificationRequest;
import com.example.jbbmobile.model.Notification;

import java.util.List;

public class NotificationController {

    private List<Notification> notificationList;
    private boolean action = false;

    public List<Notification> synchronizeNotification(Context context){
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.request(context, new NotificationRequest.Callback() {
            @Override
            public void callbackResponse(List<Notification> notificationList) {
                setNotificationList(notificationList);
                setAction(true);
            }
        });

        return notificationList;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }
}

