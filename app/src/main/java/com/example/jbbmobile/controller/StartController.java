package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.jbbmobile.dao.HelperDAO;
import java.text.ParseException;
import static android.content.Context.MODE_PRIVATE;

public class StartController {

    private final String APP_FIRST_TIME = "appFirstTime";
    private final String APP_INITIALIZED = "Init";

    public StartController(SharedPreferences settings, Context context) throws ParseException{
        if (settings.getString(APP_FIRST_TIME, null)==null) {
            HelperDAO helperDAO = new HelperDAO(context);
            helperDAO.getWritableDatabase();

            helperDAO.close();

            new BooksController().insertBooks(context);

            NotificationController notificationController = new NotificationController(context);
            notificationController.synchronizeNotification();

            SharedPreferences sharedPreferences = context.getSharedPreferences(APP_FIRST_TIME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(APP_FIRST_TIME, APP_INITIALIZED);
            editor.apply();
        }
    }
}
