package gov.jbb.missaonascente.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.BooksController;
import gov.jbb.missaonascente.controller.NotificationController;
import gov.jbb.missaonascente.model.Notification;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       registerNotification(context,intent);
    }

    public void registerNotification(Context context,Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationController notificationController = new NotificationController(context);
        Notification notification = notificationController.notificationByPeriod();

        if(notification!=null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(notification.getTitle());
            builder.setContentText(notification.getDescription());
            builder.setSmallIcon(R.drawable.icon_achievements);
            builder.setLargeIcon(selectIconNotification(context));
            builder.setContentIntent(p);
            android.app.Notification n = builder.build();

            n.vibrate = new long[]{150, 300, 150, 600};
            n.flags = android.app.Notification.FLAG_AUTO_CANCEL;
            nm.notify(R.mipmap.ic_launcher, n);

            try {
                Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone toque = RingtoneManager.getRingtone(context, som);
                toque.play();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public Bitmap selectIconNotification(Context context){
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

}
