package gov.jbb.missaonascente.controller;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.widget.Toast;

import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.VersionRequest;
import gov.jbb.missaonascente.model.Achievement;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.view.ReadQRCodeScreen;
import com.google.zxing.integration.android.IntentIntegrator;

import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MainController {
    private String code;
    private boolean action = false;
    private boolean response;
    private final String MAIN_SCREEN_FIRST_TIME = "MainScreenFirstTime";
    private final String MAIN_SCREEN_BOOLEAN = "1";

    public MainController(){}

    public MainController(Activity activity){
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(false);
        integrator.setCaptureActivity(ReadQRCodeScreen.class);
        integrator.initiateScan();
    }

    public void downloadDataFirstTime(Context context, SharedPreferences settings){
        if(settings.getString(MAIN_SCREEN_FIRST_TIME, null) == null){
            checkIfUpdateIsNeeded(context);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(MAIN_SCREEN_FIRST_TIME, MAIN_SCREEN_BOOLEAN);
            editor.apply();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void forceImageIcons(PopupMenu popupMenu){
        Object menuHelper;
        Class[] argTypes;

        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            popupMenu.show();
        }
    }

    public static boolean checkIfUserHasInternet(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void checkIfUpdateIsNeeded(final Context context){
        setAction(false);
        VersionRequest versionRequest = new VersionRequest();

        versionRequest.request(context, new VersionRequest.Callback() {
            @Override
            public void callbackResponse(double response) {
                ElementDAO database = new ElementDAO(context);
                if(database.checkVersion() == response){
                    setResponse(false);
                    Toast.makeText(context, "Não precisa atualizar", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("=======","Passou Aqui");
                    Toast.makeText(context, "Atualizando", Toast.LENGTH_SHORT).show();
                    setResponse(true);
                    ElementsController elementsController = new ElementsController();
                    QuestionController questionController = new QuestionController();
                    AlternativeController alternativeController = new AlternativeController();
                    AchievementController achievementController = new AchievementController(context);
                    elementsController.downloadElementsFromDatabase(context);
                    questionController.downloadQuestionsFromDatabase(context);
                    alternativeController.downloadAllAlternatives(context);
                    achievementController.downloadAchievementFromDatabase(context);
                    new ElementDAO(context).updateVersion((float) response);
                }
                setAction(true);
            }
        });
    }

    public void startAlarm(Context context){
        Intent intent = new Intent(context, ExplorerUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 1200000,pendingIntent);
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public ArrayList<Achievement> checkForNewElementAchievements(Context context,
                                                 HistoryController historyController, Explorer explorer) {
        AchievementController achievementController = new AchievementController(context);

        ArrayList<Achievement> newAchievements =
                achievementController.checkForNewElementAchievements(historyController, explorer);

        return newAchievements;
    }

    //TODO setar onde é respondida a questão
    public ArrayList<Achievement> checkForNewQuestionAchievements(Context context, Explorer explorer) {
        AchievementController achievementController = new AchievementController(context);

        ArrayList<Achievement> newAchievements =
                achievementController.checkForNewQuestionAchievements(explorer);

        return newAchievements;
    }
}
