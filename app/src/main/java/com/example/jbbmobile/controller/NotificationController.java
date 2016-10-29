package com.example.jbbmobile.controller;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jbbmobile.R;
import com.example.jbbmobile.dao.NotificationDAO;

import com.example.jbbmobile.dao.NotificationRequest;
import com.example.jbbmobile.model.Notification;
import com.example.jbbmobile.view.StartScreenActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationController {

    private List<Notification> notificationList;
    private boolean action = false;
    private Context context;

    public NotificationController(Context context){
        this.context = context;
    }

    public List<Notification> synchronizeNotification(){
        final NotificationDAO notificationDAO = new NotificationDAO(context);
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.request(context, new NotificationRequest.Callback() {
            @Override
            public void callbackResponse(List<Notification> notificationList) {
                insertAllNotification(notificationDAO,notificationList);
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

    public void insertAllNotification(NotificationDAO notificationDAO , List<Notification> notificationList){
        for( Notification notification : notificationList){
            try {
                notificationDAO.insertNotification(notification);
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    public void notificationByPeriod(){
            int systemMonth = 0;
            int systemDay = 0;
            long date;
            date = System.currentTimeMillis();

            SimpleDateFormat monthSimpleDateFormat = new SimpleDateFormat("MM");
            String month = monthSimpleDateFormat.format(date);
            systemMonth = Integer.valueOf(month);

            SimpleDateFormat daySimpleDateFormat = new SimpleDateFormat("dd");
            String day = daySimpleDateFormat.format(date);
            systemDay = Integer.valueOf(day);

            NotificationDAO notificationDAO = new NotificationDAO(context);

            List <Notification> notifications =  notificationDAO.findAllNotification();

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Log.i("========","-"+systemDay+"-"+systemMonth+"-----"+notifications.size()+System.currentTimeMillis());

        for(Notification notification : notifications){
                try {
                    Date dateNotification = (Date)formatter.parse(notification.getDate());

                    String monthNotificationFormat = monthSimpleDateFormat.format(dateNotification);
                    int monthNotification = Integer.valueOf(monthNotificationFormat);

                    String dayNotificationFormat = daySimpleDateFormat.format(dateNotification);
                    int dayNotification = Integer.valueOf(dayNotificationFormat);

                    Log.i("========",dayNotification+"-"+systemDay+"   "+monthNotification+"-"+systemMonth);
                    if(monthNotification == systemMonth && dayNotification == systemDay){
                        registerNotification(notification,new Intent(context,StartScreenActivity.class));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

    }

    public void registerNotification(Notification notification, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(notification.getTitle());
        builder.setContentText(notification.getDescription());
        builder.setSmallIcon(R.drawable.icon_achievements);
        builder.setLargeIcon(selectIconNotification());
        builder.setContentIntent(p);
        android.app.Notification n = builder.build();


        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = android.app.Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.mipmap.ic_launcher, n);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        }
        catch(Exception ignored){}
    }

    private Bitmap selectIconNotification(){
        BooksController booksController = new BooksController();
        int idImage;
        booksController.currentPeriod();
        int currentPeriod = booksController.getCurrentPeriod();

        if(currentPeriod==1){
            idImage = R.drawable.book_icon_open_orange;
        }else if(currentPeriod==2){
            idImage = R.drawable.book_icon_open_green;
        }else{
            idImage = R.drawable.book_icon_open_blue;
        }
        return BitmapFactory.decodeResource(context.getResources(), idImage);
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }
}

