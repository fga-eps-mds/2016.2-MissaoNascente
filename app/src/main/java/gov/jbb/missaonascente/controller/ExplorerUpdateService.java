package gov.jbb.missaonascente.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class ExplorerUpdateService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new ExplorerUpdateService.Update().start();
        return super.onStartCommand(intent, flags, startId);
    }

    private class Update extends Thread{
        @Override
        public void run() {
            ExplorerController explorerController = new ExplorerController();
            LoginController loginController = new LoginController();
            loginController.loadFile(getApplicationContext());

            explorerController.updateExplorerScore(getApplicationContext(),
                    loginController.getExplorer().getScore(),
                    loginController.getExplorer().getEmail());

            explorerController.sendElementsExplorerTable(getApplicationContext(),
                    loginController.getExplorer().getEmail());
        }
    }
}
