package com.example.jbbmobile.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.BooksController;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {
    private static final String TAG = "missaonascente-d1c85";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

    }

    @Override
    public void zzm(Intent intent){
        Intent launchIntent = new Intent(this, StartScreenActivity.class);
        launchIntent.setAction(Intent.ACTION_MAIN);
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* R    equest code */, launchIntent, PendingIntent.FLAG_ONE_SHOT);

        Bitmap imageIcon = selectIconNotification();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_achievements)
                .setLargeIcon(imageIcon)
                .setContentTitle(intent.getStringExtra("gcm.notification.title"))
                .setContentText(intent.getStringExtra("gcm.notification.body") + " " + intent.getStringExtra("gcm.notification.badge"))
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(intent.getIntExtra("fcm.notification.id",0), notificationBuilder.build());
    }

    public Bitmap selectIconNotification(){
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
        return BitmapFactory.decodeResource(getResources(), idImage);
    }

}
