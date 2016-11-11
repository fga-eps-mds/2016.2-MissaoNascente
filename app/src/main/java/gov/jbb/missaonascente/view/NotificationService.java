package gov.jbb.missaonascente.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import gov.jbb.missaonascente.R;
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

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.icon_achievements)
            .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ponto_34))
            .setContentTitle(intent.getStringExtra("gcm.notification.title"))
            .setContentText(intent.getStringExtra("gcm.notification.body"))
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(intent.getIntExtra("fcm.notification.id",0), notificationBuilder.build());
    }


}
