package gov.jbb.missaonascente.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExplorerUpdateReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        MainController mainController = new MainController();
        if(mainController.checkIfUserHasInternet(context)) {

            Log.d("ExplorerUpdateReceiver","BroadCast");

            Intent update = new Intent(context, ExplorerUpdateService.class);
            context.startService(update);

            mainController.startAlarm(context);
        }
    }
}
