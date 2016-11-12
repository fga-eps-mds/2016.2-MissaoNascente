package gov.jbb.missaonascente.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AppUpdateService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Update().start();
        return super.onStartCommand(intent, flags, startId);
    }

    private class Update extends Thread{
        @Override
        public void run() {
            MainController mainController = new MainController();
            mainController.checkIfUpdateIsNeeded(getApplicationContext());
        }
    }
}
