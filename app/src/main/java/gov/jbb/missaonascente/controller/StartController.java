package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.content.SharedPreferences;

import gov.jbb.missaonascente.dao.HelperDAO;

import java.text.ParseException;

import static android.content.Context.MODE_PRIVATE;

public class StartController {

    public StartController(SharedPreferences settings, Context context) throws ParseException{
        if (settings.getString("appFirstTime", null)==null) {
            HelperDAO helperDAO = new HelperDAO(context);
            helperDAO.getWritableDatabase();

            helperDAO.close();

            new BooksController().insertBooks(context);

            NotificationController notificationController = new NotificationController(context);
            notificationController.synchronizeNotification();
            SharedPreferences sharedPreferences = context.getSharedPreferences("appFirstTime", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("appFirstTime", "Init");
            editor.apply();
        }
    }
}
